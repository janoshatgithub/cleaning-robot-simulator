package dk.jsh.cleaningrobotsimulator.concurrent;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Field UsedBy and status
 *
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

    public Field(int column, int row, Status status, UsedBy usedBy) {
        this.column = column;
        this.row = row;
        this.status = status;
        this.usedBy = usedBy;
        this.jLabel = new JLabel();
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UsedBy getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(UsedBy usedBy) {
        this.usedBy = usedBy;
    }

    public boolean isEmpty() {
        return usedBy == UsedBy.EMPTY;
    }

    public boolean isDirty() {
        return status == Status.DIRTY;
    }

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
