package dk.jsh.cleaningrobotsimulator.concurrent;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import org.jdesktop.application.ResourceMap;

/**
 * Abstract class with common thread functions
 * @author Jan S. Hansen
 */
abstract public class BaseThread extends Thread {
    protected JTextArea jTextArea;
    protected Board board;
    protected ResourceMap resourceMap;
    protected Logger exceptionLogger; //Logging of exceptions in a log file.

    /**
     * Constructor.
     * @param threadName thread name
     * @param board A Board object
     * @param jTextArea A JTextArea to use as log for this thread
     * @param resourceMap A ResourceMap
     */
    public BaseThread(String threadName, Board board, JTextArea jTextArea,
            ResourceMap resourceMap) {
        this.board = board;
        this.jTextArea = jTextArea;
        this.resourceMap = resourceMap;
        //If an exceptions occurs, the this name will be part of the exception
        //stacktrace.
        this.setName(threadName);
        exceptionLogger = Logger.getLogger(Robot.class.getName());
        setUncaughtExceptionHandler(new SimpleThreadExceptionHandler());
    }

    /**
     * Log a message a the JTestArea. See constructor. 
     * @param message message to log.
     */
    protected void log(String message) {
        //Clear textArea after 2000 lines. TODO: Create a FIFO JTextArea
        if (jTextArea.getLineCount() > 2000) {
            jTextArea.setText("");
        }

        StringBuilder timeAndMessage =
                new StringBuilder(Constants.timeFormat.format(new Date()));
        timeAndMessage.append(" ").append(message).append("\n");
        jTextArea.append(timeAndMessage.toString());
    }

    /**
     * Log that an exception has occured in the thread.
     */
    protected void logException() {
        log("The thread is stopped, due to an exception, see log file.");
    }
}