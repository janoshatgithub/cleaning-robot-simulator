/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.jsh.cleaningrobotsimulator.concurrent;

import javax.swing.JLabel;

/**
 * A read only Field interface. Used by Board, so it can return a read only
 * field.
 * @author Jan S. Hansen
 */
public interface ReadOnlyField {
    public enum Status {CLEAN, DIRTY, DUSTBIN}
    public enum UsedBy {BENDER, WALL_E, ANDROID, EMPTY}

    public int getColumn();
    public int getRow();
    public Status getStatus();
    public UsedBy getUsedBy();
    public boolean isEmpty();
    public boolean isDirty();
    public JLabel getLabel();
}
