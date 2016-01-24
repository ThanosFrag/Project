package Server.anaptDiktua.graphics;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyRenderer extends DefaultTableCellRenderer  
{ 
    public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column) 
{ 
    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 

    if(column == 1){
    	if (value.equals("online"))
    		c.setForeground(new java.awt.Color(0, 255, 0));
    	else
    		c.setForeground(new java.awt.Color(255, 0, 0));
    }
    else
    	c.setForeground(table.getForeground());  

    return c; 
} 

} 
