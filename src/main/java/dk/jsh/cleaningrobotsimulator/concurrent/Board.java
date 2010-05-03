package dk.jsh.cleaningrobotsimulator.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import org.jdesktop.application.ResourceMap;

/**
 * Board status. 
 * @author Jan S. Hansen
 */
public class Board {

    private Field[][] board;
    private ResourceMap resourceMap;
    private int dirtyFieldsCounter;
    private int fieldsCleand;
    private JTextArea jTextAreaDustbin;
    protected SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

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

    public synchronized boolean tryMove(int fromColumn, int fromRow,
            int toColumn, int toRow, String robotIconResource) {
        testFieldArguments(fromColumn, fromRow);
        testFieldArguments(toColumn, toRow);
        Field fromField = getField(fromColumn, fromRow);
        Field toField = getField(toColumn, toRow);
        boolean moveOk = false;
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
                toField.jLabel.setIcon(resourceMap.getIcon("RobotSimulator.recycle"));
            } else {
                toField.jLabel.setIcon(resourceMap.getIcon(robotIconResource));
            }
        }
        return moveOk;
    }

    public synchronized boolean tryMakeFieldDirty(int column, int row) {
        boolean ok = false;
        if (dirtyFieldsCounter + 1 <= Constants.MAX_DIRTY_FIELDS) {
            testFieldArguments(column, row);
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

    public synchronized void cleanField(int column, int row) {
        testFieldArguments(column, row);
        if (column == 0 && row == 0) { //Dustbin
            throw new IllegalArgumentException("Dustbin can't be cleaned");
        }
        Field field = getField(column, row);
        if (!field.isDirty()) {
            throw new IllegalArgumentException("Field is not dirty");
        }
        field.setStatus(Field.Status.CLEAN);
        dirtyFieldsCounter--;
    }

    public synchronized void emptyRobot(String robotName) {
        fieldsCleand = fieldsCleand + Constants.MAX_CLEANED_FIELDS;
        //Clear textArea after 2000 lines. TODO: Create a FIFO JTextArea
        if (jTextAreaDustbin.getLineCount() > 2000) {
            jTextAreaDustbin.setText("");
        }

        StringBuilder timeAndMessage =
                new StringBuilder(timeFormat.format(new Date()));
        timeAndMessage.append(" Dust from ").append(robotName);
        timeAndMessage.append(" recieved - Total recieved: ");
        timeAndMessage.append(fieldsCleand).append(".\n");
        jTextAreaDustbin.append(timeAndMessage.toString());
    }

    private void setField(int column, int row, Field.Status status,
            Field.UsedBy usedBy, String iconResource) {
        testFieldArguments(column, row);
        Field field = board[row][column];
        field.setStatus(status);
        field.setUsedBy(usedBy);
        ImageIcon imageIcon = resourceMap.getImageIcon(iconResource);
        field.jLabel.setIcon(imageIcon);
    }

    public Field getField(int column, int row) {
        testFieldArguments(column, row);
        return board[row][column];
    }

    private void testFieldArguments(int column, int row)
            throws IllegalArgumentException {
        if (column < 0 || column >= Constants.MAX_COLUMNS
                || row < 0 || row >= Constants.MAX_ROWS) {
            throw new IllegalArgumentException("Error in column or row: ("
                    + column + ", " + row + ")");
        }
    }

    public int getDirtyFieldsCounter() {
        return dirtyFieldsCounter;
    }
}
