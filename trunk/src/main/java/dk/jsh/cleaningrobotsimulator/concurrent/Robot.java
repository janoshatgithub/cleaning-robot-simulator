package dk.jsh.cleaningrobotsimulator.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import org.jdesktop.application.ResourceMap;

/**
 * Robot thread.
 *
 * @author Jan S. Hansen
 */
public class Robot extends Thread {
    public final String RES_DIRT = "RobotSimulator.dirt";
    public final String RES_DUSTBIN = "RobotSimulator.dustbin";
    public final String RES_CLEAN = "RobotSimulator.clean";

    private boolean stopRequested = false;
    private boolean pauseRequested = false;

    private JLabel[][] uiBoard;
    private JTextArea jTextArea;
    private String resource;
    private String name;
    private Board board;
    private int column;
    private int row;
    private ResourceMap resourceMap;

    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public Robot(JLabel[][] uiBoard, JTextArea jTextArea, ResourceMap resourceMap,
            String resource, String name, int row, int column) {
        this.uiBoard = uiBoard;
        this.jTextArea = jTextArea;
        this.resource = resource;
        this.name = name;
        this.column = column;
        this.row = row;
        board = Board.getInstance();
        this.resourceMap = resourceMap;
    }

    @Override
    public void run() {
        log("Thread for robot " + name + " is now running.");
        while (!isStopRequested()) {
            if (pauseRequested) {
                paused();
            }
            else {
                cleaning();
            }
        }
        log("Thread for robot " + name + " is now stopped");
    }

    private void cleaning() {
        try {
            int toColumn = column;
            int toRow = row + 1;
            if (board.tryMove(column, row, toColumn, toRow)) {
                uiBoard[toRow][toColumn].setIcon(resourceMap.getIcon(resource));
                uiBoard[row][column].setIcon(resourceMap.getIcon(RES_CLEAN));
                row++;
            }
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void paused() {
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void requestStop() {
        log("Stop requested for robot " + name + ".");
        stopRequested = true;
    }

    private synchronized boolean isStopRequested() {
        return stopRequested;
    }

    public synchronized void requestPause() {
        log("Pause requested for robot " + name + ".");
        pauseRequested = true;
    }

    public synchronized void continueAfterPause() {
        log("Continue requested for robot " + name + ".");
        pauseRequested = false;
    }

    private synchronized boolean isPaueseRequested() {
        return stopRequested;
    }

    private void log(String message) {
        StringBuilder timeAndMessage =
                new StringBuilder(timeFormat.format(new Date()));
        timeAndMessage.append(" ").append(message).append("\n");
        jTextArea.append(timeAndMessage.toString());
    }
}