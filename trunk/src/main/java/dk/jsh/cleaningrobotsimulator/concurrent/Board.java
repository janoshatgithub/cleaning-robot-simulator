package dk.jsh.cleaningrobotsimulator.concurrent;

import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import org.jdesktop.application.ResourceMap;

/**
 * A Board. Consist of 10x10 Fields. (See fields).</br>
 * Each field can be either clean or dirty (one is the Dustbin).
 * A field can only hold one robot.</br>
 * Objects of this class is thread safe.
 * @author Jan S. Hansen
 */
public class Board {

    //Thread safety - following fields is guarded by "this".
    private Field[][] board;
    private int dirtyFieldsCounter;
    private long fieldsCleaned;
    private JTextArea jTextAreaDustbin;

    //Fields that is not changed by this class.
    private ResourceMap resourceMap;

    /**
     * Constructor.
     */
    public Board(ResourceMap resourceMap, JTextArea jTextAreaDustbin) {
        this.resourceMap = resourceMap;
        this.jTextAreaDustbin = jTextAreaDustbin;
        board = new Field[Constants.MAX_ROWS][Constants.MAX_COLUMNS];
        //Clean board
        for (int row = 0; row < Constants.MAX_ROWS; row++) {
            for (int column = 0; column < Constants.MAX_COLUMNS; column++) {
                Field field = new Field(column, row,
                        Field.Status.CLEAN, Field.UsedBy.EMPTY);
                Icon icon = resourceMap.getIcon("RobotSimulator.clean");
                field.jLabel.setIcon(icon);
                board[row][column] = field;
            }
        }
        setField(9, 0, Field.Status.CLEAN, Field.UsedBy.BENDER,
                "RobotSimulator.bender");
        setField(9, 9, Field.Status.CLEAN, Field.UsedBy.WALL_E,
                "RobotSimulator.wall-e");
        setField(0, 9, Field.Status.CLEAN, Field.UsedBy.ANDROID,
                "RobotSimulator.android");
        setField(0, 0, Field.Status.DUSTBIN, Field.UsedBy.EMPTY,
                "RobotSimulator.dustbin");
    }

    /**
     * Try to move a robot from one field to another field.
     * @param fromColumn from column
     * @param fromRow from row
     * @param toColumn to column
     * @param toRow to row
     * @param robotIconResource robot icon resource
     * @return true if move was a success.
     */
    public boolean tryMove(int fromColumn, int fromRow,
            int toColumn, int toRow, String robotIconResource) {
        testFieldArguments(fromColumn, fromRow);
        testFieldArguments(toColumn, toRow);
        boolean moveOk = false;
        synchronized (this) {
            Field fromField = getField(fromColumn, fromRow);
            Field toField = getField(toColumn, toRow);
            if (toField.isEmpty() && !fromField.isEmpty()) {
                toField.setUsedBy(fromField.getUsedBy());
                fromField.setUsedBy(Field.UsedBy.EMPTY);
                moveOk = true;
                //Set icons
                if (fromColumn == 0 && fromRow == 0) {
                    fromField.jLabel.setIcon(
                            resourceMap.getIcon("RobotSimulator.dustbin"));
                } else {
                    if (fromField.isDirty()) {
                        fromField.jLabel.setIcon(
                                resourceMap.getIcon("RobotSimulator.dirt"));
                    } else {
                        fromField.jLabel.setIcon(
                                resourceMap.getIcon("RobotSimulator.clean"));
                    }
                }
                if (toRow == 0 && toColumn == 0) {
                    toField.jLabel.setIcon(resourceMap.getIcon(
                            "RobotSimulator.recycle"));
                } else {
                    toField.jLabel.setIcon(resourceMap.getIcon(robotIconResource));
                }
            }
            return moveOk;
        }
    }

    /**
     * Try to make a field dirty.
     * @param column fields column
     * @param row fields row
     * @return true if it was a success.
     */
    public boolean tryMakeFieldDirty(int column, int row) {
        testFieldArguments(column, row);
        boolean ok = false;
        synchronized (this) {
            if (dirtyFieldsCounter + 1 <= Constants.MAX_DIRTY_FIELDS) {
                if (column == 0 && row == 0) { //Dustbin
                    throw new IllegalArgumentException("Dustbin can't be dirty");
                }
                Field field = getField(column, row);
                if (field.isEmpty() && !field.isDirty()) {
                    field.setStatus(Field.Status.DIRTY);
                    dirtyFieldsCounter++;
                    ok = true;
                    field.jLabel.setIcon(
                            resourceMap.getIcon("RobotSimulator.dirt"));
                }
            }
            return ok;
        }
    }

    /**
     * Changes a fields status to clean.
     * @param column fields column
     * @param row fields row
     * @return true if it was a success.
     */
    public boolean tryCleanField(int column, int row) {
        boolean ok = false;
        testFieldArguments(column, row);
        synchronized (this) {
            if (column == 0 && row == 0) { //Dustbin
                throw new IllegalArgumentException("Dustbin can't be cleaned");
            }
            Field field = getField(column, row);
            if (field.isDirty()) {
                field.setStatus(Field.Status.CLEAN);
                dirtyFieldsCounter--;
                ok = true;
            }
            return ok;
        }
    }

    /**
     * Empties a robot for dust and log a message to the Dustbin log.
     * @param robotName robot name, used in log message.
     */
    public synchronized void emptyRobot(String robotName) {
        fieldsCleaned+=Constants.MAX_CLEANED_FIELDS;
        //Clear textArea after 2000 lines. TODO: Create a FIFO JTextArea
        if (jTextAreaDustbin.getLineCount() > 2000) {
            jTextAreaDustbin.setText("");
        }

        StringBuilder timeAndMessage =
                new StringBuilder(Constants.timeFormat.format(new Date()));
        timeAndMessage.append(" Dust from ").append(robotName);
        timeAndMessage.append(" recieved - Total recieved: ");
        timeAndMessage.append(fieldsCleaned).append(".\n");
        jTextAreaDustbin.append(timeAndMessage.toString());
    }

    /**
     * Returns dirty fields counter.
     * @return dirty fields counter
     */
    public synchronized int getDirtyFieldsCounter() {
        return dirtyFieldsCounter;
    }

    /**
     * Returns a field.
     * @param column fields column
     * @param row fields row
     * @return field a Field
     */
    public Field getField(int column, int row) {
        testFieldArguments(column, row);
        synchronized (this) {
            return board[row][column];
        }
    }

    /**
     * Set a Fields Status and UsedBy.
     * @param column Fields column
     * @param row Fields row
     * @param status Fields Status
     * @param usedBy Fields UsedBy
     * @param iconResource Icon resource
     */
    private void setField(int column, int row, Field.Status status,
            Field.UsedBy usedBy, String iconResource) {
        testFieldArguments(column, row);
        Field field = board[row][column];
        field.setStatus(status);
        field.setUsedBy(usedBy);
        ImageIcon imageIcon = resourceMap.getImageIcon(iconResource);
        field.jLabel.setIcon(imageIcon);
    }

    /**
     * Test if is is valid column and row arguments.
     * @param column column
     * @param row row
     * @throws IllegalArgumentException Illegal row or column.
     */
    private void testFieldArguments(int column, int row)
            throws IllegalArgumentException {
        if (column < 0 || column >= Constants.MAX_COLUMNS
                || row < 0 || row >= Constants.MAX_ROWS) {
            throw new IllegalArgumentException("Error in column or row: ("
                    + column + ", " + row + ")");
        }
    }
}
