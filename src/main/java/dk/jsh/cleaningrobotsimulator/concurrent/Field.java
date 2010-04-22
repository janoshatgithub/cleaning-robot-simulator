package dk.jsh.cleaningrobotsimulator.concurrent;

/**
 * Field status value object.
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

    public Field(int column, int row, Status status, UsedBy usedBy) {
        this.column = column;
        this.row = row;
        this.status = status;
        this.usedBy = usedBy;
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
}
