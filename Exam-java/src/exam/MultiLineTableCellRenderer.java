/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exam;

/**
 *
 * @author Brandon Shi
 */
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class MultiLineTableCellRenderer extends JList<String> implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //make multi line where the cell value is String[]
        if (value instanceof String[]) {
            setListData((String[]) value);
        }
        
        //Set the column size
        setSize(table.getColumnModel().getColumn(column).getWidth(), Short.MAX_VALUE);
        
        //Make the rows change height dynamically
        int rowHeight = this.getPreferredSize().height+10;
        if (rowHeight != table.getRowHeight(row)) {
           table.setRowHeight(row, rowHeight);
        }
        
        //cell backgroud color when selected
        setSelectionBackground(new java.awt.Color(0, 0, 102));
        setBackground(new java.awt.Color(0, 0, 51));
        setForeground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("Lucida Grande", 0, 14));
        
        return this;
    }
}