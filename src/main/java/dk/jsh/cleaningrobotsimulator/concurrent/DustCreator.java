package dk.jsh.cleaningrobotsimulator.concurrent;

import java.util.Date;
import java.util.Random;
import javax.swing.JTextArea;
import org.jdesktop.application.ResourceMap;

/**
 * Dust creator thread.
 * @author Jan S. Hansen
 */
public class DustCreator extends CommonThread {
    Random randomGenerator = new Random();

    public DustCreator(String threadName, Board board, JTextArea jTextArea,
            ResourceMap resourceMap) {
        super(threadName, board, jTextArea, resourceMap);
    }

    @Override
    public void run() {
        log("Thread for dust creator is now running.");
        int dirtyFields = board.getDirtyFieldsCounter();
        log("Dirty fields on board: " + dirtyFields);
        while (dirtyFields < Constants.MAX_DIRTY_FIELDS) {
            int row = randomGenerator.nextInt(Constants.MAX_ROWS);
            int column = randomGenerator.nextInt(Constants.MAX_COLUMNS);
            if (row != 0 || column != 0) { //Dustbin
                logTrySetFieldDirty(row, column);
                if (board.tryMakeFieldDirty(column, row)) {
                    Field field = board.getField(column, row);
                    field.jLabel.setIcon(
                            resourceMap.getIcon("RobotSimulator.dirt"));
                    dirtyFields++;
                    log("Dirt added.");
                }
                else {
                    log("Failed.");
                }
            }
        }
       log("Thread for dust creator is now finished.");
    }

    private void logTrySetFieldDirty(int row, int column) {
        StringBuilder timeAndMessage =
                new StringBuilder(timeFormat.format(new Date()));
        timeAndMessage.append(" Try put dirt on field ");
        timeAndMessage.append((char)(column + 65));
        timeAndMessage.append(++row).append(".\n");;
        jTextArea.append(timeAndMessage.toString());
    }
}