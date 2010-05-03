package dk.jsh.cleaningrobotsimulator.concurrent;

import javax.swing.JLabel;

/**
 * Field value object.
 * @author Jan S. Hansen
 */
public class Field {
    public enum Status {CLEAN, DIRTY, DUSTBIN}
    public enum UsedBy {BENDER, WALL_E, ANDROID, EMPTY}

    private Status status;
    private UsedBy usedBy;
    private int column;
    private int row;
    public JLabel jLabel;

    /**
     * Constructor.
     * @param column Fields column
     * @param row Fields row
     * @param status Fields Status
     * @param usedBy Fields UsedBy
     */
    public Field(int column, int row, Status status, UsedBy usedBy) {
        this.column = column;
        this.row = row;
        this.status = status;
        this.usedBy = usedBy;
        this.jLabel = new JLabel();
    }

    /**
     * Gets Fields column.
     * @return column number
     */
    public int getColumn() {
        return column;
    }

    /**
     * Gets Fields row.
     * @return row number
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets Fields Status.
     * @return Status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets Fields Status
     * @param status Status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets Fields UsedBt
     * @return UsedBy
     */
    public UsedBy getUsedBy() {
        return usedBy;
    }

    /**
     * Sets Fields UsedBy
     * @param usedBy UsedBy
     */
    public void setUsedBy(UsedBy usedBy) {
        this.usedBy = usedBy;
    }

    /**
     * Returns true if Field is empty.
     * @return true if empty
     */
    public boolean isEmpty() {
        return usedBy == UsedBy.EMPTY;
    }

    /**
     * Returns true if Field is dirty.
     * @return true if dirty
     */
    public boolean isDirty() {
        return status == Status.DIRTY;
    }

    /**
     * Test if this field is equal to a given object.</br>
     * Row and column is tested.
     * @param obj object to Test
     * @return true if equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Field other = (Field) obj;
        if (this.column != other.column) {
            return false;
        }
        if (this.row != other.row) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.column;
        hash = 61 * hash + this.row;
        return hash;
    }
}
