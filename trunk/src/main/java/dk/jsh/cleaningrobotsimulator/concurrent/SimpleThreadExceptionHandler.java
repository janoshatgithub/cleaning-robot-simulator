package dk.jsh.cleaningrobotsimulator.concurrent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to handle uncaught exceptions in threads.
 * @author Jan S. Hansen
 */
public class SimpleThreadExceptionHandler 
        implements Thread.UncaughtExceptionHandler {
    private Logger logger;

    /**
     * Constructor.
     */
    public SimpleThreadExceptionHandler() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * Log uncaugth exceptions to a log file and to the standard error stream.
     * @param thread The thread that throw the exception
     * @param exception Exception.
     */
    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        exception.printStackTrace();
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        logger.log(Level.SEVERE, "Uncaught exception in thread",
                thread.getName());
        logger.log(Level.SEVERE, "Uncaught exception in thread", sw.toString());
        if (thread instanceof Robot) {
            Robot robot = (Robot)thread;
            robot.logException();
        }
    }
}