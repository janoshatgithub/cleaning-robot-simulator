package dk.jsh.cleaningrobotsimulator.concurrent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to handle uncaught exceptions in threads.
 *
 * @author Jan S. Hansen
 */
public class SimpleThreadExceptionHandler 
        implements Thread.UncaughtExceptionHandler {
    private Logger logger;

    public SimpleThreadExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        logger.log(Level.SEVERE, "Uncaught exception in thread",
                t.getName());
        logger.log(Level.SEVERE, "Uncaught exception in thread", sw.toString());
        if (t instanceof Robot) {
            Robot robot = (Robot)t;
            robot.logException();
        }
    }
}