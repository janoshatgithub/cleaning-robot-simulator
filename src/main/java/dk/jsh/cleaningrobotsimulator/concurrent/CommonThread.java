package dk.jsh.cleaningrobotsimulator.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import org.jdesktop.application.ResourceMap;

/**
 * Abstract class with common thread functions
 * @author Jan S. Hansen
 */
abstract public class CommonThread extends Thread {
    protected JTextArea jTextArea;
    protected Board board;
    protected ResourceMap resourceMap;
    protected Logger exceptionLogger; //Logging of exceptions in a log file.

    protected SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public CommonThread(String threadName, Board board, JTextArea jTextArea,
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

    protected void log(String message) {
        //Clear textArea after 2000 lines. TODO: Create a FIFO JTextArea
        if (jTextArea.getLineCount() > 2000) {
            jTextArea.setText("");
        }

        StringBuilder timeAndMessage =
                new StringBuilder(timeFormat.format(new Date()));
        timeAndMessage.append(" ").append(message).append("\n");
        jTextArea.append(timeAndMessage.toString());
    }

    protected void logException() {
        log("The thread is stopped, due to an exception, see log file.");
    }
}
