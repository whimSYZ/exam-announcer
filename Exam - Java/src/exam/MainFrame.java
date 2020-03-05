/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exam;

//import com.apple.eawt.Application;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;

import java.util.*;
import java.util.Timer;

import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 *
 * @author Brandon Shi
 */
public class MainFrame extends javax.swing.JFrame {

    private List<Announcement> announcements = new ArrayList();//The announcements is chosen to be an ArrayList, due to the several advantages on the specific needs of this program. 
    private final ImageIcon examIcon = new ImageIcon(getClass().getResource("/exam/folder/examIcon.png"));
    private final ImageIcon examIconLow = new ImageIcon(getClass().getResource("/exam/folder/examIconLow.png"));
    private final AddExamFrame addFrame = new AddExamFrame(this);
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        
        //Application.getApplication().setDockIconImage(new ImageIcon(getClass().getResource("/exam/examIcon.png")).getImage());//Change the dock icon for mac\
        
        //Add the timer here
        final Timer timer = new Timer();
        DateTimeFormatter secFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter minFormat = DateTimeFormatter.ofPattern("HH:mm");
                 
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                LocalDateTime currentTime = LocalDateTime.now();
                TimeLabel.setText(currentTime.format(secFormat));//First, replace the label text
                
                Announcement mockAnnouncement = new Announcement(currentTime.toLocalTime(),new String[]{""});//Make a "mock" announcement to compare with 
                int i = Collections.binarySearch(announcements, mockAnnouncement);//Use a binary search as announcements is always sorted.
                
                if( i >= 0 && !announcements.get(i).isDisplayed()){//if the search returns a valid result, and the result has not yet been displayed
                    Announcement announcement = announcements.get(i);
                    setAlwaysOnTop(true);//Force the window to be on top
                    JOptionPane pane = new JOptionPane();  
                    pane.setMessage(announcements.get(i).getContent());
                    pane.setIcon(examIconLow);//set the icon to be the low resolution
                        
                    JDialog dialog = pane.createDialog("New Announcement");
                    dialog.setAlwaysOnTop(true);//Place the dialog on the top
                    dialog.show();
                    announcement.display();
                }
                setAlwaysOnTop(false);//Reset forcing the window on top
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);

        //Resize the columns
        mainTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        mainTable.getColumnModel().getColumn(1).setPreferredWidth(600);

        //Eliminate table header
        mainTable.setTableHeader(null);

        //set TableCellRenderer into a specified JTable column class
        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
        mainTable.getColumnModel().getColumn(1).setCellRenderer(renderer);

        //Change the color of the JScrollPane to the same color as background. If not it will be white
        examScrollPane.getViewport().setBackground(MainPanel.getBackground());

        //Make a default exam
        LocalTime time = LocalTime.now();
        Exam e = new Exam("DP1 Math HL", 120, time, 0.1);
        //addExam(e);
    }

    public void addExam(Exam exam) {//addExam() is public so that AddExamFrame can access it

        //Instantiate each announcement objects
        Announcement start = new Announcement(exam.startTime(), new String[]{exam.getName() + " has started"});
        announcements.add(start);
        Announcement reading = new Announcement(exam.readingTime(), new String[]{exam.getName() + " 5 minutes reading time is over"});
        announcements.add(reading);
        Announcement thirty = new Announcement(exam.thirtyMinutes(), new String[]{exam.getName() + " has 30 minutes remaining"});
        announcements.add(thirty);
        Announcement fifteen = new Announcement(exam.fifteenMinutes(), new String[]{exam.getName() + " has 15 minutes remaining, not allowed to leave"});
        announcements.add(fifteen);
        Announcement five = new Announcement(exam.fiveMinutes(), new String[]{exam.getName() + " has 5 minutes remaining"});
        announcements.add(five);
        Announcement end = new Announcement(exam.endTime(), new String[]{exam.getName() + " has ended"});
        announcements.add(end);

        //Sort the list announcements everytime in case the newly added announcements are in earlier time.
        //The compareTo() method in the Announcement class is implicitly called.
        //Collections.sort(announcements);
        Announcement[] aList = announcements.toArray(new Announcement[announcements.size()]);

        aList = MergeSortAnnouncements.mergeSort(aList);
        System.out.println("new one");

        announcements = new ArrayList(Arrays.asList(aList));

        //Format the LocalTime objects in the form of Hour:Minutes, and then add each announcement object into the tableData
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        Object[][] tableData = new Object[announcements.size()][2];
        for (int i = 0; i < announcements.size(); i++) {
            tableData[i][0] = announcements.get(i).getTime().format(format);//Store the time in the format of Hour: Minutes
            tableData[i][1] = announcements.get(i).getContent();//Store the content as a String value
        }

        //Get the table model
        DefaultTableModel model = (DefaultTableModel) mainTable.getModel();

        //Remove all the old rows
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        //Add the new rows
        for (int i = 0; i < announcements.size(); i++) {
            model.insertRow(i, tableData[i]);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        MainPanel = new javax.swing.JPanel();
        examToolBar = new javax.swing.JToolBar();
        toolBarPanel = new javax.swing.JPanel();
        TimeLabel = new javax.swing.JLabel();
        refreshLabel = new javax.swing.JLabel();
        addLabel = new javax.swing.JLabel();
        examScrollPane = new javax.swing.JScrollPane();
        mainTable = new javax.swing.JTable();
        exportButton = new javax.swing.JButton();

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(examIcon.getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        MainPanel.setBackground(new java.awt.Color(0, 0, 51));
        MainPanel.setPreferredSize(new java.awt.Dimension(540, 360));

        examToolBar.setBackground(new java.awt.Color(51, 0, 153));
        examToolBar.setBorder(null);
        examToolBar.setFloatable(false);
        examToolBar.setRollover(true);

        toolBarPanel.setBackground(new java.awt.Color(51, 0, 153));

        TimeLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        TimeLabel.setForeground(new java.awt.Color(255, 255, 255));
        TimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TimeLabel.setText("Time");
        TimeLabel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                TimeLabelComponentShown(evt);
            }
        });

        refreshLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exam/folder/refresh.png"))); // NOI18N
        refreshLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshLabelMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshLabelMouseEntered(evt);
            }
        });

        addLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exam/folder/add.png"))); // NOI18N
        addLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                addLabelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                addLabelMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addLabelMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addLabelMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout toolBarPanelLayout = new javax.swing.GroupLayout(toolBarPanel);
        toolBarPanel.setLayout(toolBarPanelLayout);
        toolBarPanelLayout.setHorizontalGroup(
            toolBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolBarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(refreshLabel)
                .addGap(283, 283, 283)
                .addComponent(TimeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 304, Short.MAX_VALUE)
                .addComponent(addLabel)
                .addContainerGap())
        );
        toolBarPanelLayout.setVerticalGroup(
            toolBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolBarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(toolBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(refreshLabel)
                    .addComponent(addLabel)
                    .addComponent(TimeLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        examToolBar.add(toolBarPanel);

        examScrollPane.setBackground(new java.awt.Color(0, 0, 51));
        examScrollPane.setForeground(new java.awt.Color(51, 0, 102));
        examScrollPane.setColumnHeaderView(null);
        examScrollPane.setEnabled(false);
        examScrollPane.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N

        mainTable.setBackground(new java.awt.Color(0, 0, 51));
        mainTable.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        mainTable.setForeground(new java.awt.Color(255, 255, 255));
        mainTable.setModel(new MyTableModel());
        mainTable.setToolTipText("");
        mainTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        mainTable.setName(""); // NOI18N
        mainTable.setRowHeight(100);
        mainTable.setRowMargin(10);
        mainTable.setSelectionBackground(new java.awt.Color(0, 0, 102));
        mainTable.setShowGrid(false);
        mainTable.setTableHeader(null);
        mainTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mainTableKeyPressed(evt);
            }
        });
        examScrollPane.setViewportView(mainTable);

        exportButton.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        exportButton.setText("Export");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(examToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(examScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(exportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addComponent(examToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(examScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TimeLabelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_TimeLabelComponentShown
        // TODO add your handling code here:

    }//GEN-LAST:event_TimeLabelComponentShown

    private void addLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addLabelMouseClicked
        //Open the new add exam frame
        addFrame.setSize(new Dimension(400, 360));
        addFrame.setLocationRelativeTo(addLabel);
        addFrame.setVisible(true);
    }//GEN-LAST:event_addLabelMouseClicked

    private void addLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addLabelMouseEntered
        addLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exam/folder/addHovered.png")));
    }//GEN-LAST:event_addLabelMouseEntered

    private void addLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addLabelMousePressed
        addLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exam/folder/addClicked.png")));
    }//GEN-LAST:event_addLabelMousePressed

    private void addLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addLabelMouseReleased
        addLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exam/folder/add.png")));
    }//GEN-LAST:event_addLabelMouseReleased

    private void addLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addLabelMouseExited
        addLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exam/folder/add.png")));
    }//GEN-LAST:event_addLabelMouseExited

    private void refreshLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshLabelMouseClicked
        // TODO add your handling code here:
        refreshLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exam/folder/refreshClicked.png")));

        DefaultTableModel model = (DefaultTableModel) mainTable.getModel();
        model.setRowCount(0);//Remove rows
        announcements.removeAll(announcements);//Remove all data
    }//GEN-LAST:event_refreshLabelMouseClicked

    private void refreshLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshLabelMouseEntered
        // TODO add your handling code here:
        refreshLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exam/folder/refreshHovered.png")));
    }//GEN-LAST:event_refreshLabelMouseEntered

    private void refreshLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshLabelMouseExited
        // TODO add your handling code here:
        refreshLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exam/folder/refresh.png")));
    }//GEN-LAST:event_refreshLabelMouseExited

    private void mainTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mainTableKeyPressed
        // if the DELETE key(8) is pressed
        if (evt.getKeyCode() == 8) {
            DefaultTableModel model = (DefaultTableModel) mainTable.getModel();//Get the table model
            int[] selection = mainTable.getSelectedRows();//Get the selected rows

            for (int i = 0; i < selection.length; i++) {//Iterate through the selection
                //For each element selected, iterate through the announcements, check if the content of the announceemnt is the same as the one in the table cell
                for (int j = announcements.size() - 1; j >= 0; j--) {
                    //The for loop is iterated backwards 
                    if (announcements.get(j).getContent().equals(model.getValueAt(i, 1))) {
                        announcements.remove(j);//If the content in announcements list and table list are the same, remove it
                    }
                }
            }

            for (int i = selection.length - 1; i >= 0; i--) {
                model.removeRow(selection[i]);
            }
        }
    }//GEN-LAST:event_mainTableKeyPressed

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        // TODO add your handling code here:
        String result = "";
        for (int i = 0; i < announcements.size(); i++) {
            result += announcements.get(i).toString();
        }
        String fileName = JOptionPane.showInputDialog("Please enter a file name");

        try{
            String user = System.getProperty("user.home");
            File file = new File(user + "/Desktop/" + fileName + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(result);
            writer.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }//GEN-LAST:event_exportButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        try {
            FileOutputStream fileOutPut = new FileOutputStream("/tmp/save.tmp");
            ObjectOutputStream objectOutPut = new ObjectOutputStream(fileOutPut);
            
            objectOutPut.writeObject(announcements);
            
            objectOutPut.close();
            fileOutPut.close();
            
            System.out.println("Serialized data is saved in /tmp/save.tmp");
        }catch(IOException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        try {
            FileInputStream fileInput = new FileInputStream("/tmp/save.tmp");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
         
            announcements=(List<Announcement>) objectInput.readObject();
         
            objectInput.close();
            fileInput.close();
         
        }catch(IOException io) {
            JOptionPane.showMessageDialog(null, io.toString());
        }catch(ClassNotFoundException c) {
            JOptionPane.showMessageDialog(null, c.toString());
        }
        
        //Format the LocalTime objects in the form of Hour:Minutes, and then add each announcement object into the tableData
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        Object[][] tableData = new Object[announcements.size()][2];
        for (int i = 0; i < announcements.size(); i++) {
            tableData[i][0] = announcements.get(i).getTime().format(format);//Store the time in the format of Hour: Minutes
            tableData[i][1] = announcements.get(i).getContent();//Store the content as a String value
        }

        //Get the table model
        DefaultTableModel model = (DefaultTableModel) mainTable.getModel();
        //Remove all the old rows
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        //Add the new rows
        for (int i = 0; i < announcements.size(); i++) {
            model.insertRow(i, tableData[i]);
        }
        
        System.out.println("Serialized data is opened from /tmp/save.tmp");
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
 /*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanel;
    private javax.swing.JLabel TimeLabel;
    private javax.swing.JLabel addLabel;
    private javax.swing.JScrollPane examScrollPane;
    private javax.swing.JToolBar examToolBar;
    private javax.swing.JButton exportButton;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTable mainTable;
    private javax.swing.JLabel refreshLabel;
    private javax.swing.JPanel toolBarPanel;
    // End of variables declaration//GEN-END:variables
}
