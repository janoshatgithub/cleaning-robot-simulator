package dk.jsh.cleaningrobotsimulator.concurrent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import org.jdesktop.application.ResourceMap;

/**
 * Robot thread.
 *
 * @author Jan S. Hansen
 */
public class Robot extends Thread {
    public final String RES_DIRT = "RobotSimulator.dirt";
    public final String RES_DUSTBIN = "RobotSimulator.dustbin";
    public final String RES_CLEAN = "RobotSimulator.clean";
    public final String RES_RECYCLE = "RobotSimulator.recycle";

    private boolean stopRequested = false;
    private boolean pauseRequested = false;

    private JLabel[][] uiBoard;
    private JTextArea jTextArea;
    private String resource;
    private Board board;
    private int column;
    private int row;
    private ResourceMap resourceMap;
    private Field[] prevFields = new Field[]{null, null};
    private int nextPrevField = 0;

    Random randomGenerator = new Random();
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public Robot(JLabel[][] uiBoard, JTextArea jTextArea, ResourceMap resourceMap,
            String resource, int row, int column) {
        this.uiBoard = uiBoard;
        this.jTextArea = jTextArea;
        this.resource = resource;
        this.column = column;
        this.row = row;
        board = Board.getInstance();
        this.resourceMap = resourceMap;
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
        int toColumn = moveToField.getColumn();
        int toRow = moveToField.getRow();
        logMove("Try move", row, column, toRow, toColumn);
        if (board.tryMove(column, row, toColumn, toRow)) {
            if (toRow == 0 && toColumn == 0) { //To Dustbin
                uiBoard[toRow][toColumn].setIcon(resourceMap.getIcon(RES_RECYCLE));
            }
            else {
                uiBoard[toRow][toColumn].setIcon(resourceMap.getIcon(resource));
            }
            if (row == 0 && column == 0) { //From dustbin
                uiBoard[row][column].setIcon(resourceMap.getIcon(RES_DUSTBIN));
            }
            else {
                uiBoard[row][column].setIcon(resourceMap.getIcon(RES_CLEAN));
            }
            logMove("Move", row, column, toRow, toColumn);
            row = toRow;
            column = toColumn;
        }
        else {
            log("Move failed.");
        }
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void paused() {
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
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
        
        logMoveToOptions(moveToOptions);

        //Return random
        int index = randomGenerator.nextInt(moveToOptions.size());
        return moveToOptions.get(index);
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
        StringBuilder timeAndMessage =
                new StringBuilder(timeFormat.format(new Date()));
        timeAndMessage.append(" ").append(message).append("\n");
        jTextArea.append(timeAndMessage.toString());
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