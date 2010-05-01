package dk.jsh.cleaningrobotsimulator.ui.swing;

import dk.jsh.cleaningrobotsimulator.concurrent.Board;
import dk.jsh.cleaningrobotsimulator.concurrent.Constants;
import dk.jsh.cleaningrobotsimulator.concurrent.DustCreator;
import dk.jsh.cleaningrobotsimulator.concurrent.Field;
import dk.jsh.cleaningrobotsimulator.concurrent.Robot;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The application's main frame.
 */
public class View extends FrameView {
    private Board board;
    private ResourceMap resourceMap;
    private Robot bender;
    private Robot android;
    private Robot wallE;
    private Logger logger;

    public View(SingleFrameApplication app) {
        super(app);
        logger = Logger.getLogger(View.class.getName());

        resourceMap = getResourceMap();

        //Cacth windowClosing event
        JFrame jFrame = this.getFrame();
        jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent winEvt) {
                quit();
            }
        });
        
        //Set icon in upper left corner
        ImageIcon image = resourceMap.getImageIcon("RobotSimulator.recycle");
        jFrame.setIconImage(image.getImage());

        //Create board
        //board = Board.createInstance(resourceMap);
        board = new Board(resourceMap);

        //Initialize UI
        initComponents();

        //Set tab icons
        jTabbedPane1.setIconAt(0, resourceMap.getIcon("RobotSimulator.bender"));
        jTabbedPane1.setIconAt(1, resourceMap.getIcon("RobotSimulator.wall-e"));
        jTabbedPane1.setIconAt(2, resourceMap.getIcon("RobotSimulator.android"));
        jTabbedPane1.setIconAt(3, resourceMap.getIcon("RobotSimulator.dirt"));
        jTabbedPane1.setIconAt(4, resourceMap.getIcon("RobotSimulator.dustbin"));
        jTabbedPane1.setSelectedIndex(0);

        createUIBoard();

        jButtonContinue.setEnabled(false);

        //Start robot threads
        bender = new Robot("Bender", board, jTextAreaBender, resourceMap,
                "RobotSimulator.bender", 0, 9);
        bender.start();

        android = new Robot("Android", board, jTextAreaAndroid, resourceMap,
                "RobotSimulator.android", 9, 0);
        android.start();

        wallE = new Robot("Wall-E", board, jTextAreaWallE, resourceMap,
                "RobotSimulator.wall-e", 9, 9);
        wallE.start();

        //Get a scheduler
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        //Run DustCreator with a 5 secs. delay between each run.
        scheduler.scheduleWithFixedDelay(
                new DustCreator("DustCreator", board, jTextAreaDust,
                resourceMap), 0, 5, TimeUnit.SECONDS);
    }

    private void createUIBoard() {
        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        Insets insets = new Insets(1, 1, 1, 1);
        for (int row = 0; row < Constants.MAX_ROWS; row++) {
            for (int column = 0; column < Constants.MAX_COLUMNS; column++) {
                Field field = board.getField(column, row);
                JLabel jLabel = field.jLabel;
                gridBagConstraints.gridx = column + 1;
                gridBagConstraints.gridy = row + 1;
                gridBagConstraints.insets = insets;
                mainPanel.add(jLabel, gridBagConstraints);
            }
        }
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = CleaningRobotSimulator.getApplication().getMainFrame();
            aboutBox = new AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        CleaningRobotSimulator.getApplication().show(aboutBox);
    }

    @Action
    public void pause() {
        bender.requestPause();
        android.requestPause();
        wallE.requestPause();
        jButtonPause.setEnabled(false);
        jButtonContinue.setEnabled(true);
    }

    @Action
    public void cont() {
        bender.continueAfterPause();
        android.continueAfterPause();
        wallE.continueAfterPause();
        jButtonPause.setEnabled(true);
        jButtonContinue.setEnabled(false);
    }

    @Action
    public void quit() {
        bender.requestStop();
        android.requestStop();
        wallE.requestStop();
        while (bender.isAlive() || android.isAlive() || wallE.isAlive()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        logger.log(Level.INFO, "Application stopped.");
        System.exit(0);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaBender = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaWallE = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaAndroid = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaDust = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jButtonPause = new javax.swing.JButton();
        jButtonContinue = new javax.swing.JButton();

        menuBar.setName("menuBar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(dk.jsh.cleaningrobotsimulator.ui.swing.CleaningRobotSimulator.class).getContext().getResourceMap(View.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(dk.jsh.cleaningrobotsimulator.ui.swing.CleaningRobotSimulator.class).getContext().getActionMap(View.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setIcon(resourceMap.getIcon("exitMenuItem.icon")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setIcon(resourceMap.getIcon("aboutMenuItem.icon")); // NOI18N
        aboutMenuItem.setDisabledIcon(resourceMap.getIcon("aboutMenuItem.disabledIcon")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        mainPanel.setMinimumSize(new java.awt.Dimension(313, 240));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        mainPanel.add(jLabel1, gridBagConstraints);

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        mainPanel.add(jLabel2, new java.awt.GridBagConstraints());

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        mainPanel.add(jLabel3, new java.awt.GridBagConstraints());

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N
        mainPanel.add(jLabel4, new java.awt.GridBagConstraints());

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        mainPanel.add(jLabel5, new java.awt.GridBagConstraints());

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        mainPanel.add(jLabel6, new java.awt.GridBagConstraints());

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N
        mainPanel.add(jLabel7, new java.awt.GridBagConstraints());

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N
        mainPanel.add(jLabel8, new java.awt.GridBagConstraints());

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N
        mainPanel.add(jLabel9, new java.awt.GridBagConstraints());

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        mainPanel.add(jLabel10, new java.awt.GridBagConstraints());

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N
        mainPanel.add(jLabel11, new java.awt.GridBagConstraints());

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        mainPanel.add(jLabel12, gridBagConstraints);

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        mainPanel.add(jLabel13, gridBagConstraints);

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        mainPanel.add(jLabel14, gridBagConstraints);

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        mainPanel.add(jLabel15, gridBagConstraints);

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        mainPanel.add(jLabel16, gridBagConstraints);

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        mainPanel.add(jLabel17, gridBagConstraints);

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        mainPanel.add(jLabel18, gridBagConstraints);

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        mainPanel.add(jLabel19, gridBagConstraints);

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        mainPanel.add(jLabel20, gridBagConstraints);

        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        mainPanel.add(jLabel21, gridBagConstraints);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextAreaBender.setColumns(20);
        jTextAreaBender.setEditable(false);
        jTextAreaBender.setRows(5);
        jTextAreaBender.setName("jTextAreaBender"); // NOI18N
        jScrollPane1.setViewportView(jTextAreaBender);

        jTabbedPane1.addTab(resourceMap.getString("jScrollPane1.TabConstraints.tabTitle"), jScrollPane1); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextAreaWallE.setColumns(20);
        jTextAreaWallE.setEditable(false);
        jTextAreaWallE.setRows(5);
        jTextAreaWallE.setName("jTextAreaWallE"); // NOI18N
        jScrollPane2.setViewportView(jTextAreaWallE);

        jTabbedPane1.addTab(resourceMap.getString("jScrollPane2.TabConstraints.tabTitle"), jScrollPane2); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTextAreaAndroid.setColumns(20);
        jTextAreaAndroid.setEditable(false);
        jTextAreaAndroid.setRows(5);
        jTextAreaAndroid.setName("jTextAreaAndroid"); // NOI18N
        jScrollPane3.setViewportView(jTextAreaAndroid);

        jTabbedPane1.addTab(resourceMap.getString("jScrollPane3.TabConstraints.tabTitle"), jScrollPane3); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTextAreaDust.setColumns(20);
        jTextAreaDust.setEditable(false);
        jTextAreaDust.setRows(5);
        jTextAreaDust.setName("jTextAreaDust"); // NOI18N
        jScrollPane4.setViewportView(jTextAreaDust);

        jTabbedPane1.addTab(resourceMap.getString("jScrollPane4.TabConstraints.tabTitle"), jScrollPane4); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTextArea5.setColumns(20);
        jTextArea5.setEditable(false);
        jTextArea5.setRows(5);
        jTextArea5.setName("jTextArea5"); // NOI18N
        jScrollPane5.setViewportView(jTextArea5);

        jTabbedPane1.addTab(resourceMap.getString("jScrollPane5.TabConstraints.tabTitle"), jScrollPane5); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 15;
        gridBagConstraints.gridheight = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        mainPanel.add(jTabbedPane1, gridBagConstraints);

        jButtonPause.setAction(actionMap.get("pause")); // NOI18N
        jButtonPause.setText(resourceMap.getString("jButtonPause.text")); // NOI18N
        jButtonPause.setName("jButtonPause"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        mainPanel.add(jButtonPause, gridBagConstraints);

        jButtonContinue.setAction(actionMap.get("cont")); // NOI18N
        jButtonContinue.setText(resourceMap.getString("jButtonContinue.text")); // NOI18N
        jButtonContinue.setName("jButtonContinue"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        mainPanel.add(jButtonContinue, gridBagConstraints);

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonContinue;
    private javax.swing.JButton jButtonPause;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextAreaAndroid;
    private javax.swing.JTextArea jTextAreaBender;
    private javax.swing.JTextArea jTextAreaDust;
    private javax.swing.JTextArea jTextAreaWallE;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

    private JDialog aboutBox;
}
