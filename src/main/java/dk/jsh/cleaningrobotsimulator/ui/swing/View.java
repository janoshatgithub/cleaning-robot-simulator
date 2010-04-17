/*
 * View.java
 */

package dk.jsh.cleaningrobotsimulator.ui.swing;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class View extends FrameView {

    public View(SingleFrameApplication app) {
        super(app);

        initComponents();
        ResourceMap resourceMap = getResourceMap();
        jLabelA1.setIcon(resourceMap.getIcon("RobotSimulator.dustbin"));
        jLabelA2.setIcon(resourceMap.getIcon("RobotSimulator.dirt"));
        jLabelA3.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelA4.setIcon(resourceMap.getIcon("RobotSimulator.bender"));
        jLabelA5.setIcon(resourceMap.getIcon("RobotSimulator.wall-e"));
        jLabelA6.setIcon(resourceMap.getIcon("RobotSimulator.android"));

        jLabelA7.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelA8.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelA9.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelA10.setIcon(resourceMap.getIcon("RobotSimulator.clean"));

        jLabelB1.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelC1.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelD1.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelE1.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelF1.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelG1.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelH1.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelI1.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelJ1.setIcon(resourceMap.getIcon("RobotSimulator.clean"));

        jLabelB2.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelC2.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelD2.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelE2.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelF2.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelG2.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelH2.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelI2.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        jLabelJ2.setIcon(resourceMap.getIcon("RobotSimulator.clean"));
        



        // status bar initialization - message timeout, idle icon and busy animation, etc
        
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

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
        jLabelA1 = new javax.swing.JLabel();
        jLabelA2 = new javax.swing.JLabel();
        jLabelA3 = new javax.swing.JLabel();
        jLabelA4 = new javax.swing.JLabel();
        jLabelA5 = new javax.swing.JLabel();
        jLabelA6 = new javax.swing.JLabel();
        jLabelA7 = new javax.swing.JLabel();
        jLabelA8 = new javax.swing.JLabel();
        jLabelA9 = new javax.swing.JLabel();
        jLabelA10 = new javax.swing.JLabel();
        jLabelB1 = new javax.swing.JLabel();
        jLabelB2 = new javax.swing.JLabel();
        jLabelB3 = new javax.swing.JLabel();
        jLabelB4 = new javax.swing.JLabel();
        jLabelB5 = new javax.swing.JLabel();
        jLabelB6 = new javax.swing.JLabel();
        jLabelB7 = new javax.swing.JLabel();
        jLabelB8 = new javax.swing.JLabel();
        jLabelB9 = new javax.swing.JLabel();
        jLabelB10 = new javax.swing.JLabel();
        jLabelC1 = new javax.swing.JLabel();
        jLabelC2 = new javax.swing.JLabel();
        jLabelC3 = new javax.swing.JLabel();
        jLabelC4 = new javax.swing.JLabel();
        jLabelC5 = new javax.swing.JLabel();
        jLabelC6 = new javax.swing.JLabel();
        jLabelC7 = new javax.swing.JLabel();
        jLabelC8 = new javax.swing.JLabel();
        jLabelC9 = new javax.swing.JLabel();
        jLabelC10 = new javax.swing.JLabel();
        jLabelD1 = new javax.swing.JLabel();
        jLabelD2 = new javax.swing.JLabel();
        jLabelD3 = new javax.swing.JLabel();
        jLabelD4 = new javax.swing.JLabel();
        jLabelD5 = new javax.swing.JLabel();
        jLabelD6 = new javax.swing.JLabel();
        jLabelD7 = new javax.swing.JLabel();
        jLabelD8 = new javax.swing.JLabel();
        jLabelD9 = new javax.swing.JLabel();
        jLabelD10 = new javax.swing.JLabel();
        jLabelE1 = new javax.swing.JLabel();
        jLabelE2 = new javax.swing.JLabel();
        jLabelE3 = new javax.swing.JLabel();
        jLabelE4 = new javax.swing.JLabel();
        jLabelE5 = new javax.swing.JLabel();
        jLabelE6 = new javax.swing.JLabel();
        jLabelE7 = new javax.swing.JLabel();
        jLabelE8 = new javax.swing.JLabel();
        jLabelE9 = new javax.swing.JLabel();
        jLabelE10 = new javax.swing.JLabel();
        jLabelF1 = new javax.swing.JLabel();
        jLabelF2 = new javax.swing.JLabel();
        jLabelF3 = new javax.swing.JLabel();
        jLabelF4 = new javax.swing.JLabel();
        jLabelF5 = new javax.swing.JLabel();
        jLabelF6 = new javax.swing.JLabel();
        jLabelF7 = new javax.swing.JLabel();
        jLabelF8 = new javax.swing.JLabel();
        jLabelF9 = new javax.swing.JLabel();
        jLabelF10 = new javax.swing.JLabel();
        jLabelG1 = new javax.swing.JLabel();
        jLabelG2 = new javax.swing.JLabel();
        jLabelG3 = new javax.swing.JLabel();
        jLabelG4 = new javax.swing.JLabel();
        jLabelG5 = new javax.swing.JLabel();
        jLabelG6 = new javax.swing.JLabel();
        jLabelG7 = new javax.swing.JLabel();
        jLabelG8 = new javax.swing.JLabel();
        jLabelG9 = new javax.swing.JLabel();
        jLabelG10 = new javax.swing.JLabel();
        jLabelH1 = new javax.swing.JLabel();
        jLabelH2 = new javax.swing.JLabel();
        jLabelH3 = new javax.swing.JLabel();
        jLabelH4 = new javax.swing.JLabel();
        jLabelH5 = new javax.swing.JLabel();
        jLabelH6 = new javax.swing.JLabel();
        jLabelH7 = new javax.swing.JLabel();
        jLabelH8 = new javax.swing.JLabel();
        jLabelH9 = new javax.swing.JLabel();
        jLabelH10 = new javax.swing.JLabel();
        jLabelI1 = new javax.swing.JLabel();
        jLabelI2 = new javax.swing.JLabel();
        jLabelI3 = new javax.swing.JLabel();
        jLabelI4 = new javax.swing.JLabel();
        jLabelI5 = new javax.swing.JLabel();
        jLabelI6 = new javax.swing.JLabel();
        jLabelI7 = new javax.swing.JLabel();
        jLabelI8 = new javax.swing.JLabel();
        jLabelI9 = new javax.swing.JLabel();
        jLabelI10 = new javax.swing.JLabel();
        jLabelJ1 = new javax.swing.JLabel();
        jLabelJ2 = new javax.swing.JLabel();
        jLabelJ3 = new javax.swing.JLabel();
        jLabelJ4 = new javax.swing.JLabel();
        jLabelJ5 = new javax.swing.JLabel();
        jLabelJ6 = new javax.swing.JLabel();
        jLabelJ7 = new javax.swing.JLabel();
        jLabelJ8 = new javax.swing.JLabel();
        jLabelJ9 = new javax.swing.JLabel();
        jLabelJ10 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new java.awt.GridBagLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(dk.jsh.cleaningrobotsimulator.ui.swing.CleaningRobotSimulator.class).getContext().getResourceMap(View.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        mainPanel.add(jLabel1, new java.awt.GridBagConstraints());

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

        jLabelA1.setText(resourceMap.getString("jLabelA1.text")); // NOI18N
        jLabelA1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelA1.setName("jLabelA1"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jLabelA1, org.jdesktop.beansbinding.ELProperty.create("${icon}"), jLabelA1, org.jdesktop.beansbinding.BeanProperty.create("icon"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        mainPanel.add(jLabelA1, gridBagConstraints);

        jLabelA2.setText(resourceMap.getString("jLabelA2.text")); // NOI18N
        jLabelA2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelA2.setName("jLabelA2"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        mainPanel.add(jLabelA2, gridBagConstraints);

        jLabelA3.setText(resourceMap.getString("jLabelA3.text")); // NOI18N
        jLabelA3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelA3.setName("jLabelA3"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        mainPanel.add(jLabelA3, gridBagConstraints);

        jLabelA4.setText(resourceMap.getString("jLabelA4.text")); // NOI18N
        jLabelA4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelA4.setName("jLabelA4"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        mainPanel.add(jLabelA4, gridBagConstraints);

        jLabelA5.setText(resourceMap.getString("jLabelA5.text")); // NOI18N
        jLabelA5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelA5.setName("jLabelA5"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        mainPanel.add(jLabelA5, gridBagConstraints);

        jLabelA6.setText(resourceMap.getString("jLabelA6.text")); // NOI18N
        jLabelA6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelA6.setName("jLabelA6"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        mainPanel.add(jLabelA6, gridBagConstraints);

        jLabelA7.setText(resourceMap.getString("jLabelA7.text")); // NOI18N
        jLabelA7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelA7.setName("jLabelA7"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        mainPanel.add(jLabelA7, gridBagConstraints);

        jLabelA8.setText(resourceMap.getString("jLabelA8.text")); // NOI18N
        jLabelA8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelA8.setName("jLabelA8"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        mainPanel.add(jLabelA8, gridBagConstraints);

        jLabelA9.setText(resourceMap.getString("jLabelA9.text")); // NOI18N
        jLabelA9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelA9.setName("jLabelA9"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        mainPanel.add(jLabelA9, gridBagConstraints);

        jLabelA10.setText(resourceMap.getString("jLabelA10.text")); // NOI18N
        jLabelA10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelA10.setName("jLabelA10"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        mainPanel.add(jLabelA10, gridBagConstraints);

        jLabelB1.setText(resourceMap.getString("jLabelB1.text")); // NOI18N
        jLabelB1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelB1.setName("jLabelB1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        mainPanel.add(jLabelB1, gridBagConstraints);

        jLabelB2.setText(resourceMap.getString("jLabelB2.text")); // NOI18N
        jLabelB2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelB2.setName("jLabelB2"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        mainPanel.add(jLabelB2, gridBagConstraints);

        jLabelB3.setText(resourceMap.getString("jLabelB3.text")); // NOI18N
        jLabelB3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelB3.setName("jLabelB3"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        mainPanel.add(jLabelB3, gridBagConstraints);

        jLabelB4.setText(resourceMap.getString("jLabelB4.text")); // NOI18N
        jLabelB4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelB4.setName("jLabelB4"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        mainPanel.add(jLabelB4, gridBagConstraints);

        jLabelB5.setText(resourceMap.getString("jLabelB5.text")); // NOI18N
        jLabelB5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelB5.setName("jLabelB5"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        mainPanel.add(jLabelB5, gridBagConstraints);

        jLabelB6.setText(resourceMap.getString("jLabelB6.text")); // NOI18N
        jLabelB6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelB6.setName("jLabelB6"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        mainPanel.add(jLabelB6, gridBagConstraints);

        jLabelB7.setText(resourceMap.getString("jLabelB7.text")); // NOI18N
        jLabelB7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelB7.setName("jLabelB7"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        mainPanel.add(jLabelB7, gridBagConstraints);

        jLabelB8.setText(resourceMap.getString("jLabelB8.text")); // NOI18N
        jLabelB8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelB8.setName("jLabelB8"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        mainPanel.add(jLabelB8, gridBagConstraints);

        jLabelB9.setText(resourceMap.getString("jLabelB9.text")); // NOI18N
        jLabelB9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelB9.setName("jLabelB9"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        mainPanel.add(jLabelB9, gridBagConstraints);

        jLabelB10.setText(resourceMap.getString("jLabelB10.text")); // NOI18N
        jLabelB10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelB10.setName("jLabelB10"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        mainPanel.add(jLabelB10, gridBagConstraints);

        jLabelC1.setText(resourceMap.getString("jLabelC1.text")); // NOI18N
        jLabelC1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelC1.setName("jLabelC1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        mainPanel.add(jLabelC1, gridBagConstraints);

        jLabelC2.setText(resourceMap.getString("jLabelC2.text")); // NOI18N
        jLabelC2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelC2.setName("jLabelC2"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        mainPanel.add(jLabelC2, gridBagConstraints);

        jLabelC3.setText(resourceMap.getString("jLabelC3.text")); // NOI18N
        jLabelC3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelC3.setName("jLabelC3"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        mainPanel.add(jLabelC3, gridBagConstraints);

        jLabelC4.setText(resourceMap.getString("jLabelC4.text")); // NOI18N
        jLabelC4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelC4.setName("jLabelC4"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        mainPanel.add(jLabelC4, gridBagConstraints);

        jLabelC5.setText(resourceMap.getString("jLabelC5.text")); // NOI18N
        jLabelC5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelC5.setName("jLabelC5"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        mainPanel.add(jLabelC5, gridBagConstraints);

        jLabelC6.setText(resourceMap.getString("jLabelC6.text")); // NOI18N
        jLabelC6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelC6.setName("jLabelC6"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        mainPanel.add(jLabelC6, gridBagConstraints);

        jLabelC7.setText(resourceMap.getString("jLabelC7.text")); // NOI18N
        jLabelC7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelC7.setName("jLabelC7"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        mainPanel.add(jLabelC7, gridBagConstraints);

        jLabelC8.setText(resourceMap.getString("jLabelC8.text")); // NOI18N
        jLabelC8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelC8.setName("jLabelC8"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        mainPanel.add(jLabelC8, gridBagConstraints);

        jLabelC9.setText(resourceMap.getString("jLabelC9.text")); // NOI18N
        jLabelC9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelC9.setName("jLabelC9"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        mainPanel.add(jLabelC9, gridBagConstraints);

        jLabelC10.setText(resourceMap.getString("jLabelC10.text")); // NOI18N
        jLabelC10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelC10.setName("jLabelC10"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        mainPanel.add(jLabelC10, gridBagConstraints);

        jLabelD1.setText(resourceMap.getString("jLabelD1.text")); // NOI18N
        jLabelD1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelD1.setName("jLabelD1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        mainPanel.add(jLabelD1, gridBagConstraints);

        jLabelD2.setText(resourceMap.getString("jLabelD2.text")); // NOI18N
        jLabelD2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelD2.setName("jLabelD2"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        mainPanel.add(jLabelD2, gridBagConstraints);

        jLabelD3.setText(resourceMap.getString("jLabelD3.text")); // NOI18N
        jLabelD3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelD3.setName("jLabelD3"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        mainPanel.add(jLabelD3, gridBagConstraints);

        jLabelD4.setText(resourceMap.getString("jLabelD4.text")); // NOI18N
        jLabelD4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelD4.setName("jLabelD4"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        mainPanel.add(jLabelD4, gridBagConstraints);

        jLabelD5.setText(resourceMap.getString("jLabelD5.text")); // NOI18N
        jLabelD5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelD5.setName("jLabelD5"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        mainPanel.add(jLabelD5, gridBagConstraints);

        jLabelD6.setText(resourceMap.getString("jLabelD6.text")); // NOI18N
        jLabelD6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelD6.setName("jLabelD6"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        mainPanel.add(jLabelD6, gridBagConstraints);

        jLabelD7.setText(resourceMap.getString("jLabelD7.text")); // NOI18N
        jLabelD7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelD7.setName("jLabelD7"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        mainPanel.add(jLabelD7, gridBagConstraints);

        jLabelD8.setText(resourceMap.getString("jLabelD8.text")); // NOI18N
        jLabelD8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelD8.setName("jLabelD8"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        mainPanel.add(jLabelD8, gridBagConstraints);

        jLabelD9.setText(resourceMap.getString("jLabelD9.text")); // NOI18N
        jLabelD9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelD9.setName("jLabelD9"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        mainPanel.add(jLabelD9, gridBagConstraints);

        jLabelD10.setText(resourceMap.getString("jLabelD10.text")); // NOI18N
        jLabelD10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelD10.setName("jLabelD10"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        mainPanel.add(jLabelD10, gridBagConstraints);

        jLabelE1.setText(resourceMap.getString("jLabelE1.text")); // NOI18N
        jLabelE1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelE1.setName("jLabelE1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        mainPanel.add(jLabelE1, gridBagConstraints);

        jLabelE2.setText(resourceMap.getString("jLabelE2.text")); // NOI18N
        jLabelE2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelE2.setName("jLabelE2"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        mainPanel.add(jLabelE2, gridBagConstraints);

        jLabelE3.setText(resourceMap.getString("jLabelE3.text")); // NOI18N
        jLabelE3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelE3.setName("jLabelE3"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        mainPanel.add(jLabelE3, gridBagConstraints);

        jLabelE4.setText(resourceMap.getString("jLabelE4.text")); // NOI18N
        jLabelE4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelE4.setName("jLabelE4"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        mainPanel.add(jLabelE4, gridBagConstraints);

        jLabelE5.setText(resourceMap.getString("jLabelE5.text")); // NOI18N
        jLabelE5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelE5.setName("jLabelE5"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        mainPanel.add(jLabelE5, gridBagConstraints);

        jLabelE6.setText(resourceMap.getString("jLabelE6.text")); // NOI18N
        jLabelE6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelE6.setName("jLabelE6"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        mainPanel.add(jLabelE6, gridBagConstraints);

        jLabelE7.setText(resourceMap.getString("jLabelE7.text")); // NOI18N
        jLabelE7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelE7.setName("jLabelE7"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        mainPanel.add(jLabelE7, gridBagConstraints);

        jLabelE8.setText(resourceMap.getString("jLabelE8.text")); // NOI18N
        jLabelE8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelE8.setName("jLabelE8"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        mainPanel.add(jLabelE8, gridBagConstraints);

        jLabelE9.setText(resourceMap.getString("jLabelE9.text")); // NOI18N
        jLabelE9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelE9.setName("jLabelE9"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        mainPanel.add(jLabelE9, gridBagConstraints);

        jLabelE10.setText(resourceMap.getString("jLabelE10.text")); // NOI18N
        jLabelE10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelE10.setName("jLabelE10"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 10;
        mainPanel.add(jLabelE10, gridBagConstraints);

        jLabelF1.setText(resourceMap.getString("jLabelF1.text")); // NOI18N
        jLabelF1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelF1.setName("jLabelF1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        mainPanel.add(jLabelF1, gridBagConstraints);

        jLabelF2.setText(resourceMap.getString("jLabelF2.text")); // NOI18N
        jLabelF2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelF2.setName("jLabelF2"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        mainPanel.add(jLabelF2, gridBagConstraints);

        jLabelF3.setText(resourceMap.getString("jLabelF3.text")); // NOI18N
        jLabelF3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelF3.setName("jLabelF3"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        mainPanel.add(jLabelF3, gridBagConstraints);

        jLabelF4.setText(resourceMap.getString("jLabelF4.text")); // NOI18N
        jLabelF4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelF4.setName("jLabelF4"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        mainPanel.add(jLabelF4, gridBagConstraints);

        jLabelF5.setText(resourceMap.getString("jLabelF5.text")); // NOI18N
        jLabelF5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelF5.setName("jLabelF5"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        mainPanel.add(jLabelF5, gridBagConstraints);

        jLabelF6.setText(resourceMap.getString("jLabelF6.text")); // NOI18N
        jLabelF6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelF6.setName("jLabelF6"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        mainPanel.add(jLabelF6, gridBagConstraints);

        jLabelF7.setText(resourceMap.getString("jLabelF7.text")); // NOI18N
        jLabelF7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelF7.setName("jLabelF7"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 7;
        mainPanel.add(jLabelF7, gridBagConstraints);

        jLabelF8.setText(resourceMap.getString("jLabelF8.text")); // NOI18N
        jLabelF8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelF8.setName("jLabelF8"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        mainPanel.add(jLabelF8, gridBagConstraints);

        jLabelF9.setText(resourceMap.getString("jLabelF9.text")); // NOI18N
        jLabelF9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelF9.setName("jLabelF9"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 9;
        mainPanel.add(jLabelF9, gridBagConstraints);

        jLabelF10.setText(resourceMap.getString("jLabelF10.text")); // NOI18N
        jLabelF10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelF10.setName("jLabelF10"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        mainPanel.add(jLabelF10, gridBagConstraints);

        jLabelG1.setText(resourceMap.getString("jLabelG1.text")); // NOI18N
        jLabelG1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelG1.setName("jLabelG1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        mainPanel.add(jLabelG1, gridBagConstraints);

        jLabelG2.setText(resourceMap.getString("jLabelG2.text")); // NOI18N
        jLabelG2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelG2.setName("jLabelG2"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        mainPanel.add(jLabelG2, gridBagConstraints);

        jLabelG3.setText(resourceMap.getString("jLabelG3.text")); // NOI18N
        jLabelG3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelG3.setName("jLabelG3"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        mainPanel.add(jLabelG3, gridBagConstraints);

        jLabelG4.setText(resourceMap.getString("jLabelG4.text")); // NOI18N
        jLabelG4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelG4.setName("jLabelG4"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        mainPanel.add(jLabelG4, gridBagConstraints);

        jLabelG5.setText(resourceMap.getString("jLabelG5.text")); // NOI18N
        jLabelG5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelG5.setName("jLabelG5"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 5;
        mainPanel.add(jLabelG5, gridBagConstraints);

        jLabelG6.setText(resourceMap.getString("jLabelG6.text")); // NOI18N
        jLabelG6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelG6.setName("jLabelG6"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        mainPanel.add(jLabelG6, gridBagConstraints);

        jLabelG7.setText(resourceMap.getString("jLabelG7.text")); // NOI18N
        jLabelG7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelG7.setName("jLabelG7"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 7;
        mainPanel.add(jLabelG7, gridBagConstraints);

        jLabelG8.setText(resourceMap.getString("jLabelG8.text")); // NOI18N
        jLabelG8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelG8.setName("jLabelG8"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        mainPanel.add(jLabelG8, gridBagConstraints);

        jLabelG9.setText(resourceMap.getString("jLabelG9.text")); // NOI18N
        jLabelG9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelG9.setName("jLabelG9"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 9;
        mainPanel.add(jLabelG9, gridBagConstraints);

        jLabelG10.setText(resourceMap.getString("jLabelG10.text")); // NOI18N
        jLabelG10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelG10.setName("jLabelG10"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 10;
        mainPanel.add(jLabelG10, gridBagConstraints);

        jLabelH1.setText(resourceMap.getString("jLabelH1.text")); // NOI18N
        jLabelH1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelH1.setName("jLabelH1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        mainPanel.add(jLabelH1, gridBagConstraints);

        jLabelH2.setText(resourceMap.getString("jLabelH2.text")); // NOI18N
        jLabelH2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelH2.setName("jLabelH2"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        mainPanel.add(jLabelH2, gridBagConstraints);

        jLabelH3.setText(resourceMap.getString("jLabelH3.text")); // NOI18N
        jLabelH3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelH3.setName("jLabelH3"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 3;
        mainPanel.add(jLabelH3, gridBagConstraints);

        jLabelH4.setText(resourceMap.getString("jLabelH4.text")); // NOI18N
        jLabelH4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelH4.setName("jLabelH4"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        mainPanel.add(jLabelH4, gridBagConstraints);

        jLabelH5.setText(resourceMap.getString("jLabelH5.text")); // NOI18N
        jLabelH5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelH5.setName("jLabelH5"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 5;
        mainPanel.add(jLabelH5, gridBagConstraints);

        jLabelH6.setText(resourceMap.getString("jLabelH6.text")); // NOI18N
        jLabelH6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelH6.setName("jLabelH6"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        mainPanel.add(jLabelH6, gridBagConstraints);

        jLabelH7.setText(resourceMap.getString("jLabelH7.text")); // NOI18N
        jLabelH7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelH7.setName("jLabelH7"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 7;
        mainPanel.add(jLabelH7, gridBagConstraints);

        jLabelH8.setText(resourceMap.getString("jLabelH8.text")); // NOI18N
        jLabelH8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelH8.setName("jLabelH8"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        mainPanel.add(jLabelH8, gridBagConstraints);

        jLabelH9.setText(resourceMap.getString("jLabelH9.text")); // NOI18N
        jLabelH9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelH9.setName("jLabelH9"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 9;
        mainPanel.add(jLabelH9, gridBagConstraints);

        jLabelH10.setText(resourceMap.getString("jLabelH10.text")); // NOI18N
        jLabelH10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelH10.setName("jLabelH10"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 10;
        mainPanel.add(jLabelH10, gridBagConstraints);

        jLabelI1.setText(resourceMap.getString("jLabelI1.text")); // NOI18N
        jLabelI1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelI1.setName("jLabelI1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 1;
        mainPanel.add(jLabelI1, gridBagConstraints);

        jLabelI2.setText(resourceMap.getString("jLabelI2.text")); // NOI18N
        jLabelI2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelI2.setName("jLabelI2"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 2;
        mainPanel.add(jLabelI2, gridBagConstraints);

        jLabelI3.setText(resourceMap.getString("jLabelI3.text")); // NOI18N
        jLabelI3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelI3.setName("jLabelI3"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 3;
        mainPanel.add(jLabelI3, gridBagConstraints);

        jLabelI4.setText(resourceMap.getString("jLabelI4.text")); // NOI18N
        jLabelI4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelI4.setName("jLabelI4"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 4;
        mainPanel.add(jLabelI4, gridBagConstraints);

        jLabelI5.setText(resourceMap.getString("jLabelI5.text")); // NOI18N
        jLabelI5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelI5.setName("jLabelI5"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 5;
        mainPanel.add(jLabelI5, gridBagConstraints);

        jLabelI6.setText(resourceMap.getString("jLabelI6.text")); // NOI18N
        jLabelI6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelI6.setName("jLabelI6"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 6;
        mainPanel.add(jLabelI6, gridBagConstraints);

        jLabelI7.setText(resourceMap.getString("jLabelI7.text")); // NOI18N
        jLabelI7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelI7.setName("jLabelI7"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 7;
        mainPanel.add(jLabelI7, gridBagConstraints);

        jLabelI8.setText(resourceMap.getString("jLabelI8.text")); // NOI18N
        jLabelI8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelI8.setName("jLabelI8"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 8;
        mainPanel.add(jLabelI8, gridBagConstraints);

        jLabelI9.setText(resourceMap.getString("jLabelI9.text")); // NOI18N
        jLabelI9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelI9.setName("jLabelI9"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 9;
        mainPanel.add(jLabelI9, gridBagConstraints);

        jLabelI10.setText(resourceMap.getString("jLabelI10.text")); // NOI18N
        jLabelI10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelI10.setName("jLabelI10"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 10;
        mainPanel.add(jLabelI10, gridBagConstraints);

        jLabelJ1.setText(resourceMap.getString("jLabelJ1.text")); // NOI18N
        jLabelJ1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelJ1.setName("jLabelJ1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 1;
        mainPanel.add(jLabelJ1, gridBagConstraints);

        jLabelJ2.setText(resourceMap.getString("jLabelJ2.text")); // NOI18N
        jLabelJ2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelJ2.setName("jLabelJ2"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        mainPanel.add(jLabelJ2, gridBagConstraints);

        jLabelJ3.setText(resourceMap.getString("jLabelJ3.text")); // NOI18N
        jLabelJ3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelJ3.setName("jLabelJ3"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 3;
        mainPanel.add(jLabelJ3, gridBagConstraints);

        jLabelJ4.setText(resourceMap.getString("jLabelJ4.text")); // NOI18N
        jLabelJ4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelJ4.setName("jLabelJ4"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        mainPanel.add(jLabelJ4, gridBagConstraints);

        jLabelJ5.setText(resourceMap.getString("jLabelJ5.text")); // NOI18N
        jLabelJ5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelJ5.setName("jLabelJ5"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 5;
        mainPanel.add(jLabelJ5, gridBagConstraints);

        jLabelJ6.setText(resourceMap.getString("jLabelJ6.text")); // NOI18N
        jLabelJ6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelJ6.setName("jLabelJ6"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 6;
        mainPanel.add(jLabelJ6, gridBagConstraints);

        jLabelJ7.setText(resourceMap.getString("jLabelJ7.text")); // NOI18N
        jLabelJ7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelJ7.setName("jLabelJ7"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 7;
        mainPanel.add(jLabelJ7, gridBagConstraints);

        jLabelJ8.setText(resourceMap.getString("jLabelJ8.text")); // NOI18N
        jLabelJ8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelJ8.setName("jLabelJ8"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 8;
        mainPanel.add(jLabelJ8, gridBagConstraints);

        jLabelJ9.setText(resourceMap.getString("jLabelJ9.text")); // NOI18N
        jLabelJ9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelJ9.setName("jLabelJ9"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 9;
        mainPanel.add(jLabelJ9, gridBagConstraints);

        jLabelJ10.setText(resourceMap.getString("jLabelJ10.text")); // NOI18N
        jLabelJ10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabelJ10.setName("jLabelJ10"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 10;
        mainPanel.add(jLabelJ10, gridBagConstraints);

        menuBar.setName("menuBar"); // NOI18N

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

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
            .add(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statusMessageLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 141, Short.MAX_VALUE)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(statusMessageLabel)
                    .add(statusAnimationLabel)
                    .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JLabel jLabelA1;
    private javax.swing.JLabel jLabelA10;
    private javax.swing.JLabel jLabelA2;
    private javax.swing.JLabel jLabelA3;
    private javax.swing.JLabel jLabelA4;
    private javax.swing.JLabel jLabelA5;
    private javax.swing.JLabel jLabelA6;
    private javax.swing.JLabel jLabelA7;
    private javax.swing.JLabel jLabelA8;
    private javax.swing.JLabel jLabelA9;
    private javax.swing.JLabel jLabelB1;
    private javax.swing.JLabel jLabelB10;
    private javax.swing.JLabel jLabelB2;
    private javax.swing.JLabel jLabelB3;
    private javax.swing.JLabel jLabelB4;
    private javax.swing.JLabel jLabelB5;
    private javax.swing.JLabel jLabelB6;
    private javax.swing.JLabel jLabelB7;
    private javax.swing.JLabel jLabelB8;
    private javax.swing.JLabel jLabelB9;
    private javax.swing.JLabel jLabelC1;
    private javax.swing.JLabel jLabelC10;
    private javax.swing.JLabel jLabelC2;
    private javax.swing.JLabel jLabelC3;
    private javax.swing.JLabel jLabelC4;
    private javax.swing.JLabel jLabelC5;
    private javax.swing.JLabel jLabelC6;
    private javax.swing.JLabel jLabelC7;
    private javax.swing.JLabel jLabelC8;
    private javax.swing.JLabel jLabelC9;
    private javax.swing.JLabel jLabelD1;
    private javax.swing.JLabel jLabelD10;
    private javax.swing.JLabel jLabelD2;
    private javax.swing.JLabel jLabelD3;
    private javax.swing.JLabel jLabelD4;
    private javax.swing.JLabel jLabelD5;
    private javax.swing.JLabel jLabelD6;
    private javax.swing.JLabel jLabelD7;
    private javax.swing.JLabel jLabelD8;
    private javax.swing.JLabel jLabelD9;
    private javax.swing.JLabel jLabelE1;
    private javax.swing.JLabel jLabelE10;
    private javax.swing.JLabel jLabelE2;
    private javax.swing.JLabel jLabelE3;
    private javax.swing.JLabel jLabelE4;
    private javax.swing.JLabel jLabelE5;
    private javax.swing.JLabel jLabelE6;
    private javax.swing.JLabel jLabelE7;
    private javax.swing.JLabel jLabelE8;
    private javax.swing.JLabel jLabelE9;
    private javax.swing.JLabel jLabelF1;
    private javax.swing.JLabel jLabelF10;
    private javax.swing.JLabel jLabelF2;
    private javax.swing.JLabel jLabelF3;
    private javax.swing.JLabel jLabelF4;
    private javax.swing.JLabel jLabelF5;
    private javax.swing.JLabel jLabelF6;
    private javax.swing.JLabel jLabelF7;
    private javax.swing.JLabel jLabelF8;
    private javax.swing.JLabel jLabelF9;
    private javax.swing.JLabel jLabelG1;
    private javax.swing.JLabel jLabelG10;
    private javax.swing.JLabel jLabelG2;
    private javax.swing.JLabel jLabelG3;
    private javax.swing.JLabel jLabelG4;
    private javax.swing.JLabel jLabelG5;
    private javax.swing.JLabel jLabelG6;
    private javax.swing.JLabel jLabelG7;
    private javax.swing.JLabel jLabelG8;
    private javax.swing.JLabel jLabelG9;
    private javax.swing.JLabel jLabelH1;
    private javax.swing.JLabel jLabelH10;
    private javax.swing.JLabel jLabelH2;
    private javax.swing.JLabel jLabelH3;
    private javax.swing.JLabel jLabelH4;
    private javax.swing.JLabel jLabelH5;
    private javax.swing.JLabel jLabelH6;
    private javax.swing.JLabel jLabelH7;
    private javax.swing.JLabel jLabelH8;
    private javax.swing.JLabel jLabelH9;
    private javax.swing.JLabel jLabelI1;
    private javax.swing.JLabel jLabelI10;
    private javax.swing.JLabel jLabelI2;
    private javax.swing.JLabel jLabelI3;
    private javax.swing.JLabel jLabelI4;
    private javax.swing.JLabel jLabelI5;
    private javax.swing.JLabel jLabelI6;
    private javax.swing.JLabel jLabelI7;
    private javax.swing.JLabel jLabelI8;
    private javax.swing.JLabel jLabelI9;
    private javax.swing.JLabel jLabelJ1;
    private javax.swing.JLabel jLabelJ10;
    private javax.swing.JLabel jLabelJ2;
    private javax.swing.JLabel jLabelJ3;
    private javax.swing.JLabel jLabelJ4;
    private javax.swing.JLabel jLabelJ5;
    private javax.swing.JLabel jLabelJ6;
    private javax.swing.JLabel jLabelJ7;
    private javax.swing.JLabel jLabelJ8;
    private javax.swing.JLabel jLabelJ9;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
