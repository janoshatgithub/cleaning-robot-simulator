package dk.jsh.cleaningrobotsimulator.ui.swing;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Main thread uncaught exception handler.
 * @author Jan S. Hansen
 */
public class SimpleMainThreadExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Logger logger;

    /**
     * Constructor.
     */
    public SimpleMainThreadExceptionHandler() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * Log uncaugth exceptions to a log file and show an error dialog.
     * @param thread The thread that throw the exception
     * @param exception Exception to log.
     */
    @Override
    public void uncaughtException(final Thread thread,
            final Throwable exception) {
       if (SwingUtilities.isEventDispatchThread()) {
            showAndLogException(thread, exception);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    showAndLogException(thread, exception);
                }
            });
        }
    }

   /**
    * Log exception in log file and show an error dialog.
    * @param thread The thread that throw the exception
    * @param exception Exception to log.
    */
   private void showAndLogException(Thread thread, Throwable exception) {
            exception.printStackTrace();
            StringWriter sw = new StringWriter();
            exception.printStackTrace(new PrintWriter(sw));
            logger.log(Level.SEVERE, "Uncaught exception in main thread",
                    sw.toString());
            JOptionPane.showMessageDialog(null,
                "An unexpected error occured, see log file.",
                "Cleaning robot simulator error",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
   }
}