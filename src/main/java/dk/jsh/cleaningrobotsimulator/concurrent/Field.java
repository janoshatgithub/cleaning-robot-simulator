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

    public Field(Status status, UsedBy usedBy) {
        this.status = status;
        this.usedBy = usedBy;
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
