package dk.jsh.cleaningrobotsimulator.concurrent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import javax.swing.JTextArea;
import org.jdesktop.application.ResourceMap;

/**
 * Robot thread.
 *
 * @author Jan S. Hansen
 */
public class Robot extends CommonThread {
    private boolean stopRequested = false;
    private boolean pauseRequested = false;
    private String resource;
    private int column;
    private int row;
    private Field[] prevFields = new Field[]{null, null, null, null, null, null};
    private int nextPrevField;
    private int fieldsCleaned;

    Random randomGenerator = new Random();

    public Robot(String threadName, Board board, JTextArea jTextArea,
            ResourceMap resourceMap,
            String resource, int row, int column) {
        super(threadName, board, jTextArea, resourceMap);
        this.resource = resource;
        this.column = column;
        this.row = row;
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
        Field moveToField = geNextField();
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
                    if (moveToField.isDirty()) {
                        board.cleanField(toColumn, toRow);
                        fieldsCleaned++;
                        log("No of fields cleaned: " + fieldsCleaned + ".");
                    }
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

    private Field geNextField() {
        List<Field> moveToCleanFieldOptions = new ArrayList<Field>();
        List<Field> moveToDirtyFieldOptions = new ArrayList<Field>();
        //Test fields above
        int testColumn = column - 1;
        int testRow = row - 1;
        for (testColumn = column - 1; testColumn <= column + 1; testColumn++) {
            if (validRowColumn(testColumn, testRow)) {
                Field field = board.getField(testColumn, testRow);
                if (field.isEmpty() && !isFieldInPrevFields(field)) {
                    if (field.isDirty()) {
                        moveToDirtyFieldOptions.add(field);
                    }
                    else {
                        moveToCleanFieldOptions.add(field);
                    }
                }
            }
        }
        //Test field to the left
        testRow = row;
        testColumn = column - 1;
        if (validRowColumn(testColumn, testRow)) {
            Field field = board.getField(testColumn, testRow);
            if (field.isEmpty() && !isFieldInPrevFields(field)) {
                if (field.isDirty()) {
                    moveToDirtyFieldOptions.add(field);
                }
                else {
                    moveToCleanFieldOptions.add(field);
                }
            }
        }
        //Test field to the right
        testColumn = column + 1;
        if (validRowColumn(testColumn, testRow)) {
            Field field = board.getField(testColumn, testRow);
            if (field.isEmpty() && !isFieldInPrevFields(field)) {
                if (field.isDirty()) {
                    moveToDirtyFieldOptions.add(field);
                }
                else {
                    moveToCleanFieldOptions.add(field);
                }
            }
        }
        //Test fields below
        testColumn = column - 1;
        testRow = row + 1;
        for (testColumn = column - 1; testColumn <= column + 1; testColumn++) {
            if (validRowColumn(testColumn, testRow)) {
                Field field = board.getField(testColumn, testRow);
                if (field.isEmpty() && !isFieldInPrevFields(field)) {
                    if (field.isDirty()) {
                        moveToDirtyFieldOptions.add(field);
                    }
                    else {
                        moveToCleanFieldOptions.add(field);
                    }
                }
            }
        }
        Field field = null;
        if (!moveToDirtyFieldOptions.isEmpty()) {
            logMoveToOptions("Move to dirty field options",
                    moveToCleanFieldOptions);
                        //Return random
            int index = randomGenerator.nextInt(moveToDirtyFieldOptions.size());
            field = moveToDirtyFieldOptions.get(index);
        }
        else { //No dirty fields try empty clean fields
            log("No dirty fields nearby.");
            if (!moveToCleanFieldOptions.isEmpty()) {
                logMoveToOptions("Move to clean field options",
                        moveToCleanFieldOptions);
                int index = randomGenerator.nextInt(moveToCleanFieldOptions.size());
                field = moveToCleanFieldOptions.get(index);
            }
            else {
                log("*** Robot is locked, no move is possible!");
            }
        }
        return field;
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

    private void logMoveToOptions(String message, List<Field> fields) {
                StringBuilder timeAndMessage =
                new StringBuilder(timeFormat.format(new Date()));
        timeAndMessage.append(" ").append(message);
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