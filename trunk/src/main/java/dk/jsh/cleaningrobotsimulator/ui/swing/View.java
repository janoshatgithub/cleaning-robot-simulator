/*
 * View.java
 */
package dk.jsh.cleaningrobotsimulator.ui.swing;

import dk.jsh.cleaningrobotsimulator.concurrent.Board;
import dk.jsh.cleaningrobotsimulator.concurrent.Constants;
import dk.jsh.cleaningrobotsimulator.concurrent.Field;
import java.awt.GridBagConstraints;
import java.awt.Insets;
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
    public final String RES_BENDER = "RobotSimulator.bender";
    public final String RES_WALL_E = "RobotSimulator.wall-e";
    public final String RES_ANDROID = "RobotSimulator.android";
    public final String RES_DIRT = "RobotSimulator.dirt";
    public final String RES_DUSTBIN = "RobotSimulator.dustbin";
    public final String RES_CLEAN = "RobotSimulator.clean";
    private JLabel uiBoard[][];
    private Board board;
    private ResourceMap resourceMap;


    public View(SingleFrameApplication app) {
        super(app);
        board = Board.getInstance();
        resourceMap = getResourceMap();
        initComponents();

        //Set tab icons
        jTabbedPane1.setIconAt(0, resourceMap.getIcon(RES_BENDER));
        jTabbedPane1.setIconAt(1, resourceMap.getIcon(RES_WALL_E));
        jTabbedPane1.setIconAt(2, resourceMap.getIcon(RES_ANDROID));
        jTabbedPane1.setIconAt(3, resourceMap.getIcon(RES_DIRT));
        jTabbedPane1.setIconAt(4, resourceMap.getIcon(RES_DUSTBIN));

        createUIBoard();
        jButtonStop.setEnabled(false);
    }

    private void createUIBoard() {
        uiBoard = new JLabel[Constants.MAX_ROWS][Constants.MAX_COLUMNS];
        JLabel jLabel;
        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        Insets insets = new Insets(1, 1, 1, 1);
        for (int row = 0; row < Constants.MAX_ROWS; row++) {
            for (int column = 0; column < Constants.MAX_COLUMNS; column++) {
                jLabel = new JLabel();
                Field field = board.getField(column, row);
                Field.UsedBy usedBy = field.getUsedBy();
                String iconRes = RES_CLEAN;
                if (!field.isEmpty()) {
                    switch (usedBy) {
                        case ANDROID :
                            iconRes = RES_ANDROID;
                            break;
                        case WALL_E :
                            iconRes = RES_WALL_E;
                            break;
                        case BENDER :
                            iconRes = RES_BENDER;
                            break;
                        default :
                            throw new RuntimeException("Field column " + 
                                    column + " row " + row + 
                                    " is not empty. But no robot is added.");
                    }
                }
                else {
                    Field.Status status = field.getStatus();
                    switch (status) {
                        case CLEAN :
                            iconRes = RES_CLEAN;
                            break;
                        case DIRTY :
                            iconRes = RES_DIRT;
                            break;
                        case DUSTBIN :
                            iconRes = RES_DUSTBIN;
                            break;
                        default :
                            throw new RuntimeException("Field column " +
                                    column + " row " + row +
                                    " has no known status.");
                    }
                }
                jLabel.setIcon(resourceMap.getIcon(iconRes));
                gridBagConstraints.gridx = column + 1;
                gridBagConstraints.gridy = row + 1;
                gridBagConstraints.insets = insets;
                mainPanel.add(jLabel, gridBagConstraints);
                uiBoard[row][column] = jLabel;
            }
        }
    }

    /**
     * Replaces a board filed with a new icon.
     *
     * @param column Column A to H
     * @param row row 1 to 10
     * @param iconResource icon resource name
     */
    public void setFieldIcon(int column, int row, String iconResource) {
        if (column < 0 || column >= Constants.MAX_COLUMNS ||
            row < 0 || row >= Constants.MAX_ROWS) {
            throw new IllegalArgumentException();
        }
        JLabel jLabel = uiBoard[row][column];
        jLabel.setIcon(resourceMap.getIcon(iconResource));
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
    public void start() {
        //TODO
        jButtonStart.setEnabled(false);
        jButtonStop.setEnabled(true);
    }

    @Action
    public void stop() {
        //TODO
        jButtonStart.setEnabled(true);
        jButtonStop.setEnabled(false);
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
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jButtonStart = new javax.swing.JButton();
        jButtonStop = new javax.swing.JButton();

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

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jTabbedPane1.addTab(resourceMap.getString("jScrollPane1.TabConstraints.tabTitle"), jScrollPane1); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setName("jTextArea2"); // NOI18N
        jScrollPane2.setViewportView(jTextArea2);

        jTabbedPane1.addTab(resourceMap.getString("jScrollPane2.TabConstraints.tabTitle"), jScrollPane2); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setName("jTextArea3"); // NOI18N
        jScrollPane3.setViewportView(jTextArea3);

        jTabbedPane1.addTab(resourceMap.getString("jScrollPane3.TabConstraints.tabTitle"), jScrollPane3); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jTextArea4.setName("jTextArea4"); // NOI18N
        jScrollPane4.setViewportView(jTextArea4);

        jTabbedPane1.addTab(resourceMap.getString("jScrollPane4.TabConstraints.tabTitle"), jScrollPane4); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTextArea5.setColumns(20);
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

        jButtonStart.setAction(actionMap.get("start")); // NOI18N
        jButtonStart.setText(resourceMap.getString("jButtonStart.text")); // NOI18N
        jButtonStart.setName("jButtonStart"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        mainPanel.add(jButtonStart, gridBagConstraints);

        jButtonStop.setAction(actionMap.get("stop")); // NOI18N
        jButtonStop.setText(resourceMap.getString("jButtonStop.text")); // NOI18N
        jButtonStop.setName("jButtonStop"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        mainPanel.add(jButtonStop, gridBagConstraints);

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonStart;
    private javax.swing.JButton jButtonStop;
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
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

    private JDialog aboutBox;
}
