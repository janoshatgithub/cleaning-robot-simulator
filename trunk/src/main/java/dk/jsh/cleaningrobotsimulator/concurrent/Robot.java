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
 * @author Jan S. Hansen
 */
public class Robot extends BaseThread {

    private boolean stopRequested = false;
    private boolean pauseRequested = false;
    private String resource;
    private String fullResource;
    private int column;
    private int row;
    private ReadOnlyField[] prevFields =
            new ReadOnlyField[]{null, null, null, null, null, null};
    private int nextPrevField;
    private int fieldsCleaned;
    Random randomGenerator = new Random();

    /**
     * Constructor.
     * @param threadName Thread name
     * @param board A Board object
     * @param jTextArea A JTextArea to use as log for this thread
     * @param resourceMap A ResourceMap
     * @param resource Robots normal icon resource
     * @param fullResource Robots full icon resource
     * @param row Robots start row position
     * @param column Robots start column position
     */
    public Robot(String threadName, Board board, JTextArea jTextArea,
            ResourceMap resourceMap,
            String resource, String fullResource,
            int row, int column) {
        super(threadName, board, jTextArea, resourceMap);
        this.resource = resource;
        this.fullResource = fullResource;
        this.column = column;
        this.row = row;
        this.resourceMap = resourceMap;
    }

    /**
     * The threads run method.
     */
    @Override
    public void run() {
        log("Thread for robot is now running.");
        while (!isStopRequested()) {
            if (isPauseRequested()) {
                paused();
            } else {
                cleaning();
            }
        }
        log("Thread for robot is now stopped");
    }

    /**
     * Robot is in cleaning mode
     */
    private void cleaning() {
        addToPrevFields(board.getReadOnlyField(column, row));
        if (fieldsCleaned >= Constants.MAX_CLEANED_FIELDS) { //Goto bin
            gotoDustbinMode();
        } else { //Search and clean
            cleaningMode();
        }
        sleepForSecs(1);
    }

    /**
     * Search for dirty nearby fields. If found clean a random
     * dirty field, else goto randon a nearby empty and clean field.
     */
    private void cleaningMode() {
        //Search and clean
        ReadOnlyField moveToField = getNextField();
        if (moveToField == null) {
            clearPrevFields();
        } else {
            int toColumn = moveToField.getColumn();
            int toRow = moveToField.getRow();
            logMove("Try move", row, column, toRow, toColumn);
            if (board.tryMove(column, row, toColumn, toRow, resource)) {
                logMove("Moved from", row, column, toRow, toColumn);
                if (moveToField.isDirty()) {
                    if (board.tryCleanField(toColumn, toRow)) {
                        fieldsCleaned++;
                        log("Number of fields cleaned: " + fieldsCleaned + ".");
                        if (fieldsCleaned >= Constants.MAX_CLEANED_FIELDS) {
                            log("Robot is full.");
                        }
                    } else {
                        log("*** The field is no longer dirty, after moving " + "robot.");
                    }
                }
                row = toRow;
                column = toColumn;
            } else {
                log("*** Move failed.");
            }
        }
    }

    /**
     * Move robot closer to the dustbin. If robot is on the dustbin field, the
     * robot is emptied.
     */
    private void gotoDustbinMode() {
        //Goto bin
        int toRow = row > 0 ? row - 1 : 0;
        int toColumn = column > 0 ? column - 1 : 0;
        if (board.tryMove(column, row, toColumn, toRow, fullResource)) {
            logMove("Move to dustbin", row, column, toRow, toColumn);
            if (toRow == 0 && toColumn == 0) {
                fieldsCleaned = 0;
                board.emptyRobot(this.getName());
                clearPrevFields();
                log("Robot is emptied.");
            }
            row = toRow;
            column = toColumn;
        } else {
            log("*** Move to dustbin failed.");
        }
    }

    /**
     * Paused this thread for 1 second.
     */
    private void paused() {
        sleepForSecs(1);
    }

    /**
     * Makes this thread goto sleep for a given number of seconds.
     * @param secs seconds
     */
    private void sleepForSecs(int secs) {
        try {
            int msecs = secs * 1000;
            int i = 0;
            while (i < (msecs / 100) && !isStopRequested()) {
                sleep(100);
                i++;
            }
        } catch (InterruptedException ex) {
            exceptionLogger.log(Level.SEVERE, null, ex);
            logException();
            requestStop();
        }
    }

    /**
     * Request this thread to stop
     */
    public synchronized void requestStop() {
        log("Stop requested for robot.");
        stopRequested = true;
    }

    /**'
     * Returns true if this thread is requested to stop.
     * @return true if this thread is requested to stop
     */
    private synchronized boolean isStopRequested() {
        return stopRequested;
    }

    /**
     * Request this thread to go into pause mode.
     */
    public synchronized void requestPause() {
        log("Pause requested for robot.");
        pauseRequested = true;
    }

    /**
     * Request this thread to go into running mode.
     */
    public synchronized void continueAfterPause() {
        log("Continue requested for robot.");
        pauseRequested = false;
    }

    /**
     * Returns true if this thread is requested to go into pause mode.
     * @return true if this thread is requested to go into pause mode
     */
    private synchronized boolean isPauseRequested() {
        return pauseRequested;
    }

    /**
     * Returns the next field the Robot should try to go to. Dirty Fields has
     * priority.
     * @return A Field or null if no move is possible
     */
    private ReadOnlyField getNextField() {
        List<ReadOnlyField> moveToCleanFieldOptions =
                new ArrayList<ReadOnlyField>();
        List<ReadOnlyField> moveToDirtyFieldOptions =
                new ArrayList<ReadOnlyField>();
        //Test fields above
        int testColumn = column - 1;
        int testRow = row - 1;
        for (testColumn = column - 1; testColumn <= column + 1; testColumn++) {
            if (validRowColumn(testColumn, testRow)) {
                ReadOnlyField field = board.getReadOnlyField(testColumn, testRow);
                if (field.isEmpty()) {
                    if (field.isDirty()) {
                        moveToDirtyFieldOptions.add(field);
                    } else {
                         if(!isFieldInPrevFields(field)) {
                            moveToCleanFieldOptions.add(field);
                         }
                    }
                }
            }
        }
        //Test field to the left
        testRow = row;
        testColumn = column - 1;
        if (validRowColumn(testColumn, testRow)) {
            ReadOnlyField field = board.getReadOnlyField(testColumn, testRow);
            if (field.isEmpty()) {
                if (field.isDirty()) {
                    moveToDirtyFieldOptions.add(field);
                } else {
                     if(!isFieldInPrevFields(field)) {
                        moveToCleanFieldOptions.add(field);
                     }
                }
            }
        }
        //Test field to the right
        testColumn = column + 1;
        if (validRowColumn(testColumn, testRow)) {
            ReadOnlyField field = board.getReadOnlyField(testColumn, testRow);
            if (field.isEmpty()) {
                if (field.isDirty()) {
                    moveToDirtyFieldOptions.add(field);
                } else {
                     if(!isFieldInPrevFields(field)) {
                        moveToCleanFieldOptions.add(field);
                     }
                }
            }
        }
        //Test fields below
        testColumn = column - 1;
        testRow = row + 1;
        for (testColumn = column - 1; testColumn <= column + 1; testColumn++) {
            if (validRowColumn(testColumn, testRow)) {
                ReadOnlyField field =
                        board.getReadOnlyField(testColumn, testRow);
                if (field.isEmpty()) {
                    if (field.isDirty()) {
                        moveToDirtyFieldOptions.add(field);
                    } else {
                         if(!isFieldInPrevFields(field)) {
                            moveToCleanFieldOptions.add(field);
                         }
                    }
                }
            }
        }
        ReadOnlyField field = null;
        if (!moveToDirtyFieldOptions.isEmpty()) {
            logMoveToOptions("Move to dirty field options",
                    moveToDirtyFieldOptions);
            //Return random
            int index = randomGenerator.nextInt(moveToDirtyFieldOptions.size());
            field = moveToDirtyFieldOptions.get(index);
        } else { //No dirty fields to move to, try clean fields.
            log("No dirty fields nearby.");
            if (!moveToCleanFieldOptions.isEmpty()) {
                logMoveToOptions("Move to clean field options",
                        moveToCleanFieldOptions);
                int index = randomGenerator.nextInt(
                        moveToCleanFieldOptions.size());
                field = moveToCleanFieldOptions.get(index);
            } else {
                log("*** Robot is locked, no move is possible!");
            }
        }
        return field;
    }

    /**
     * Test if a pair of column and row is valid, for a move.
     * @param column Column
     * @param row Row
     * @return true if valid pair of column and row
     */
    private boolean validRowColumn(int column, int row) {
        boolean ok = true;
        if (row < 0 || row >= Constants.MAX_ROWS
                || column < 0 || column >= Constants.MAX_COLUMNS) {
            ok = false;
        }
        if (column == 0 && row == 0) { //Dustbin
            ok = false;
        }
        return ok;
    }

    /**
     * Add a Field to a circular buffer with previous fields this Robot has
     * visited.
     * @param field ReadOnlyField to add to buffer
     */
    private void addToPrevFields(ReadOnlyField field) {
        prevFields[nextPrevField] = field;
        nextPrevField++;
        if (nextPrevField > prevFields.length - 1) {
            nextPrevField = 0;
        }
    }

    /**
     * Clear a circular buffer with previous fields this Robot has
     * visited.
     */
    private void clearPrevFields() {
        log("Clear prev. fields.");
        for (int i = 0; i < prevFields.length; i++) {
            prevFields[i] = null;
        }
        nextPrevField = 0;
    }

    /**
     * Returns true if this field is in the circular buffer with previous
     * fields.
     * @param field ReadOnlyField to test
     * @return true if this field is in the circular buffer with previous
     * fields. 
     */
    private boolean isFieldInPrevFields(ReadOnlyField field) {
        int i = 0;
        boolean fieldFound = false;
        while (!fieldFound && i < prevFields.length) {
            if (field.equals(prevFields[i])) {
                fieldFound = true;
            } else {
                i++;
            }
        }
        return fieldFound;
    }

    /**
     * Log a move.
     * @param message Message before from and to text.
     * @param fromRow from row
     * @param fromColumn from column
     * @param toRow to row
     * @param toColumn to column
     */
    private void logMove(String message,
            int fromRow, int fromColumn,
            int toRow, int toColumn) {
        StringBuilder timeAndMessage =
                new StringBuilder(Constants.timeFormat.format(new Date()));
        timeAndMessage.append(" ").append(message).append(" ");
        timeAndMessage.append((char) (fromColumn + 65));
        timeAndMessage.append(++fromRow).append(" to ");
        timeAndMessage.append((char) (toColumn + 65));
        timeAndMessage.append(++toRow).append(".\n");
        jTextArea.append(timeAndMessage.toString());
    }

    /**
     * Log all move to options.
     * @param message Message before options
     * @param fields A List of Fields
     */
    private void logMoveToOptions(String message, List<ReadOnlyField> fields) {
        StringBuilder timeAndMessage =
                new StringBuilder(Constants.timeFormat.format(new Date()));
        timeAndMessage.append(" ").append(message);
        String before = ": ";
        for (ReadOnlyField field : fields) {
            timeAndMessage.append(before);
            timeAndMessage.append((char) (field.getColumn() + 65));
            timeAndMessage.append(field.getRow() + 1);
            before = ", ";
        }
        timeAndMessage.append(".\n");
        jTextArea.append(timeAndMessage.toString());
    }
}