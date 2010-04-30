package dk.jsh.cleaningrobotsimulator.concurrent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import org.jdesktop.application.ResourceMap;

/**
 * Robot thread.
 *
 * @author Jan S. Hansen
 */
public class Robot extends Thread {
    private boolean stopRequested = false;
    private boolean pauseRequested = false;

    private JTextArea jTextArea; //Use to UI log messages
    private String resource;
    private Board board;
    private int column;
    private int row;
    private ResourceMap resourceMap;
    private Field[] prevFields = new Field[]{null, null, null, null, null, null};
    private int nextPrevField = 0;
    private Logger exceptionLogger; //Logging of exceptions in a log file.

    Random randomGenerator = new Random();
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public Robot(String threadName, Board board, JTextArea jTextArea,
            ResourceMap resourceMap,
            String resource, int row, int column) {
        this.board = board;
        this.jTextArea = jTextArea;
        this.resource = resource;
        this.column = column;
        this.row = row;
        this.resourceMap = resourceMap;
        //If an exceptions occurs, the this name will be part of the exception
        //stacktrace.
        this.setName(threadName);
        exceptionLogger = Logger.getLogger(Robot.class.getName());
        setUncaughtExceptionHandler(new SimpleThreadExceptionHandler());
    }

    @Override
    public void run() {
        log("Thread for robot is now running.");
        while (!isStopRequested()) {
            if (isPauseRequested()) {
                paused();
            }
            else {
                cleaning();
            }
        }
        log("Thread for robot is now stopped");
    }

    private void cleaning() {
        addToPrevFields(board.getField(column, row));
        Field moveToField = getRandomNextField();
        if (moveToField == null) {
            clearPrevFields();
        }
        else {
            int toColumn = moveToField.getColumn();
            int toRow = moveToField.getRow();
            logMove("Try move", row, column, toRow, toColumn);
            if (board.tryMove(column, row, toColumn, toRow)) {
                if (toRow == 0 && toColumn == 0) { //To Dustbin
                    moveToField.jLabel.setIcon(resourceMap.getIcon("RobotSimulator.recycle"));
                }
                else {
                    moveToField.jLabel.setIcon(resourceMap.getIcon(resource));
                }
                Field fromField = board.getField(column, row);
                if (row == 0 && column == 0) { //From dustbin
                    fromField.jLabel.setIcon(resourceMap.getIcon("RobotSimulator.dustbin"));
                }
                else {
                    fromField.jLabel.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
                }
                logMove("Move", row, column, toRow, toColumn);
                row = toRow;
                column = toColumn;
            }
            else {
                log("Move failed.");
            }
        }
        sleepForSecs(1);
    }

    private void paused() {
        sleepForSecs(1);
    }

    private void sleepForSecs(int secs) {
        try {
            sleep(secs * 1000);
        } catch (InterruptedException ex) {
            exceptionLogger.log(Level.SEVERE, null, ex);
            logException();
            requestStop();
        }
    }

    public synchronized void requestStop() {
        log("Stop requested for robot.");
        stopRequested = true;
    }

    private synchronized boolean isStopRequested() {
        return stopRequested;
    }

    public synchronized void requestPause() {
        log("Pause requested for robot.");
        pauseRequested = true;
    }

    public synchronized void continueAfterPause() {
        log("Continue requested for robot.");
        pauseRequested = false;
    }

    private synchronized boolean isPauseRequested() {
        return pauseRequested;
    }

    private Field getRandomNextField() {
        List<Field> moveToOptions = new ArrayList<Field>();
        //Test fields above
        int testColumn = column - 1;
        int testRow = row - 1;
        for (testColumn = column - 1; testColumn <= column + 1; testColumn++) {
            if (validRowColumn(testColumn, testRow)) {
                Field field = board.getField(testColumn, testRow);
                if (field.isEmpty() && !isFieldInPrevFields(field)) {
                    moveToOptions.add(field);
                }
            }
        }
        //Test field to the left
        testRow = row;
        testColumn = column - 1;
        if (validRowColumn(testColumn, testRow)) {
            Field field = board.getField(testColumn, testRow);
            if (field.isEmpty() && !isFieldInPrevFields(field)) {
                moveToOptions.add(field);
            }
        }
        //Test field to the right
        testColumn = column + 1;
        if (validRowColumn(testColumn, testRow)) {
            Field field = board.getField(testColumn, testRow);
            if (field.isEmpty() && !isFieldInPrevFields(field)) {
                moveToOptions.add(field);
            }
        }
        //Test fields below
        testColumn = column - 1;
        testRow = row + 1;
        for (testColumn = column - 1; testColumn <= column + 1; testColumn++) {
            if (validRowColumn(testColumn, testRow)) {
                Field field = board.getField(testColumn, testRow);
                if (field.isEmpty() && !isFieldInPrevFields(field)) {
                    moveToOptions.add(field);
                }
            }
        }
        
        if (moveToOptions.isEmpty()) {
            log("*** Robot is locked, no move is possible!");
            return null;
        }
        else {
            logMoveToOptions(moveToOptions);

            //Return random
            int index = randomGenerator.nextInt(moveToOptions.size());
            return moveToOptions.get(index);
        }
    }

    private boolean validRowColumn(int column, int row) {
        boolean ok = true;
        if (row < 0 || row >= Constants.MAX_ROWS ||
            column < 0 || column >= Constants.MAX_COLUMNS) {
            ok = false;
        }
        return ok;
    }

    private void addToPrevFields(Field field) {
        prevFields[nextPrevField] = field;
        nextPrevField++;
        if (nextPrevField > prevFields.length - 1) {
            nextPrevField = 0;
        }
    }

    private void clearPrevFields() {
        log("Clear prev. fields.");
        for (int i = 0; i < prevFields.length; i++) {
            prevFields[i] = null;
        }
        nextPrevField = 0;
    }

    private boolean isFieldInPrevFields(Field field) {
        int i = 0;
        boolean fieldFound = false;
        while (!fieldFound && i < prevFields.length) {
            if (field.equals(prevFields[i])) {
                fieldFound = true;
            }
            else {
                i++;
            }
        }
        return fieldFound;
    }

    private void log(String message) {
        //Clear textArea after 2000 lines. TODO: Create a FIFO JTextArea
        if (jTextArea.getLineCount() > 2000) {
            jTextArea.setText("");
        }

        StringBuilder timeAndMessage =
                new StringBuilder(timeFormat.format(new Date()));
        timeAndMessage.append(" ").append(message).append("\n");
        jTextArea.append(timeAndMessage.toString());
    }

    protected void logException() {
        log("The thread is stopped, due to an exception, see log file.");
    }

    private void logMove(String message,
                         int fromRow, int fromColumn,
                         int toRow, int toColumn) {
        StringBuilder timeAndMessage =
                new StringBuilder(timeFormat.format(new Date()));
        timeAndMessage.append(" ").append(message).append(" ");
        timeAndMessage.append((char)(fromColumn + 65));
        timeAndMessage.append(++fromRow).append(" to ");
        timeAndMessage.append((char)(toColumn + 65));
        timeAndMessage.append(++toRow).append(".\n");
        jTextArea.append(timeAndMessage.toString());
    }

    private void logMoveToOptions(List<Field> fields) {
                StringBuilder timeAndMessage =
                new StringBuilder(timeFormat.format(new Date()));
        timeAndMessage.append(" Move to options");
        String before = ": ";
        for (Field field : fields) {
            timeAndMessage.append(before);
            timeAndMessage.append((char)(field.getColumn() + 65));
            timeAndMessage.append(field.getRow() + 1);
            before = ", ";
        }
        timeAndMessage.append(".\n");
        jTextArea.append(timeAndMessage.toString());
    }
}