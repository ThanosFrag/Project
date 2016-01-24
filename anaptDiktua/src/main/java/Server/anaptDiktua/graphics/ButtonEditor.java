package Server.anaptDiktua.graphics;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

class ButtonEditor extends DefaultCellEditor {
	  protected JButton button;

	  private String label;

	  private boolean isPushed;
	  
	  private boolean isbTask;
	  
	  private int row;
	  
	  private JTable tab;

	  public ButtonEditor(JCheckBox checkBox) {
	    super(checkBox);
	    button = new JButton();
	    button.setOpaque(true);
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        fireEditingStopped();
	      }
	    });
	  }

	  public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column) {
		this.tab = table;
		this.row = row;
	    if (isSelected) {
	    	button.setForeground(table.getSelectionForeground());
	    	button.setBackground(table.getSelectionBackground());
	    } else {
	    	button.setForeground(table.getForeground());
	    	button.setBackground(table.getBackground());
	    }
	    label = (value == null) ? "" : value.toString();
	    if (table.getColumnName(column).equals("Tasks"))
	    	isbTask = true;
	    else
	    	isbTask = false;
	    button.setText(label);
	    isPushed = true;
	    return button;
	  }

	  public Object getCellEditorValue() {
		  String hashkey = (String) this.tab.getModel().getValueAt(this.row, 0);
		  boolean isonline;
		  if (this.tab.getModel().getValueAt(this.row, 1).equals("online"))
			  isonline = true;
		  else
			  isonline = false;
		  if (isPushed && !isonline && isbTask)
			  JOptionPane.showMessageDialog(button,"You cant assign a task to an offline SA");
		  else if (isPushed && isonline && isbTask) {
			  Commands com = new Commands(hashkey);
			  com.setVisible(true);
		  }
		  else if (isPushed && !isbTask) {
			  Results res = new Results(true,hashkey);
			  res.setVisible(true);
		  }
		  isPushed = false;
		  return new String(label);
	  	}

	  public boolean stopCellEditing() {
	    isPushed = false;
	    return super.stopCellEditing();
	  }

	  protected void fireEditingStopped() {
	    super.fireEditingStopped();
	  }
	}