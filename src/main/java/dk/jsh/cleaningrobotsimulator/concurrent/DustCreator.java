package dk.jsh.cleaningrobotsimulator.concurrent;

import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import javax.swing.JTextArea;
import org.jdesktop.application.ResourceMap;

/**
 * Dust creator thread.
 * @author Jan S. Hansen
 */
public class DustCreator extends CommonThread {
    Random randomGenerator = new Random();

    /**
     * Constructor.
     * @param threadName Thread name
     * @param board A Board object
     * @param jTextArea A JTextArea to use as log for this thread.
     * @param resourceMap A ResourceMap
     */
    public DustCreator(String threadName, Board board, JTextArea jTextArea,
            ResourceMap resourceMap) {
        super(threadName, board, jTextArea, resourceMap);
    }

    /**
     * The threads run method.
     */
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
                    dirtyFields++;
                    log("Dirt added.");
                }
                else {
                    log("Failed.");
                }
            }
            sleepForSecs(1);
        }
       log("Thread for dust creator is now finished.");
    }

    /**
     * Log a "Try put dirt on field" message.
     * @param row fields row, used in log message, converted to row + 1
     * @param column fields column, used in log message, converted to A, B, C
     * etc.
     */
    private void logTrySetFieldDirty(int row, int column) {
        StringBuilder timeAndMessage =
                new StringBuilder(timeFormat.format(new Date()));
        timeAndMessage.append(" Try put dirt on field ");
        timeAndMessage.append((char)(column + 65));
        timeAndMessage.append(++row).append(".\n");;
        jTextArea.append(timeAndMessage.toString());
    }

    /**
     * Makes this thread goto sleep for a given number of seconds.
     * @param secs seconds
     */
    private void sleepForSecs(int secs) {
        try {
            sleep(secs * 1000);
        } catch (InterruptedException ex) {
            exceptionLogger.log(Level.SEVERE, null, ex);
            logException();
        }
    }
}