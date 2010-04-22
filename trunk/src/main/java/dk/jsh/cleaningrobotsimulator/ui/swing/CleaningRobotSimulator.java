/*
 * CleaningRobotSimulator.java
 */

package dk.jsh.cleaningrobotsimulator.ui.swing;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class CleaningRobotSimulator extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        try {
            Handler fh = new FileHandler("cleaning-robot-simulator.log");
            fh.setFormatter(new SimpleFormatter());
            Logger logger = Logger.getLogger("");
            logger.addHandler(fh);
            logger.setLevel(Level.INFO);
            logger.info("Application started.");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        show(new View(this));
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
        launch(CleaningRobotSimulator.class, args);
    }
}
