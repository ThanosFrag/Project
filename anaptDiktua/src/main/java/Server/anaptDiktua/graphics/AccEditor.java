package Server.anaptDiktua.graphics;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import Server.anaptDiktua.Requests.BroadcasterToClient;
import Server.anaptDiktua.Requests.ReceiveSoftwareAgents;

class AccEditor extends DefaultCellEditor {
	  protected JButton button;

	  private String label;

	  private boolean isPushed;
	  
	  private boolean isyes;
	  
	  private int row;
	  
	  private JTable tab;

	  public AccEditor(JCheckBox checkBox) {
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
	    if (table.getColumnName(column).equals("Accept"))
	    	isyes = true;
	    else
	    	isyes = false;
	    button.setText(label);
	    isPushed = true;
	    return button;
	  }

	  public Object getCellEditorValue() {
		  String hashkey = (String) this.tab.getModel().getValueAt(this.row, 0);
		  Map<String,ArrayList<String>> SAmap = ReceiveSoftwareAgents.getSAmap();
		  SAmap.remove(hashkey);
		  if (isPushed && isyes)
			  BroadcasterToClient.SendToClient(hashkey,"yes");
		  else if (isPushed && !isyes)
			  BroadcasterToClient.SendToClient(hashkey,"no");
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
