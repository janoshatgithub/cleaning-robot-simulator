package dk.jsh.cleaningrobotsimulator.ui.swing;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 *
 * @author Jan S. Hansen
 */
public class CleaningRobotSimulator extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new View(this));
    }

    private static void setupLog() {
        try {
            //%t - Means that the log is located in the Systems Temp directory
            Handler fh = new FileHandler("%t/cleaning-robot-simulator.log",
                    10000, 5);
            Logger logger = Logger.getLogger("");
            logger.addHandler(fh);
            logger.setLevel(Level.INFO);
            logger.info("Application started.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of CleaningRobotSimulator
     */
    public static CleaningRobotSimulator getApplication() {
        return Application.getInstance(CleaningRobotSimulator.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        setupLog();
        Thread.setDefaultUncaughtExceptionHandler(
                new SimpleMainThreadExceptionHandler());
        launch(CleaningRobotSimulator.class, args);
    }
}
