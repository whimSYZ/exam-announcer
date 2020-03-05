/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exam;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Brandon Shi
 */
public class MyTableModel extends DefaultTableModel {
    private String[] columnNames = new String [] {
                "Time", "Content"
    };
    private Object[][] data = new Object [][] {

    };
    private boolean[] canEdit = new boolean [] {
        false, false
    };
    
    public MyTableModel(){
        super();
        super.setDataVector(data, columnNames);
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
}