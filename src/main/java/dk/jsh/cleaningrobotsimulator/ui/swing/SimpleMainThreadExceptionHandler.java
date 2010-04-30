/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.jsh.cleaningrobotsimulator.ui.swing;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Main thread uncaught exception handler.
 *
 * @author Jan S. Hansen
 */
public class SimpleMainThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
       if (SwingUtilities.isEventDispatchThread()) {
            showAndLogException(t, e);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    showAndLogException(t, e);
                }
            });
        }
    }

   private void showAndLogException(Thread t, Throwable e) {
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Uncaught exception in main thread",
                    sw.toString());
            JOptionPane.showMessageDialog(null,
                "An unexpected error occured, see log file.",
                "Cleaning robot simulator error",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
   }
}