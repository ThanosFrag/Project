package Server.anaptDiktua.graphics;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import Server.anaptDiktua.import_updateDB.ImportUpdateDB;

public class Results extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private static boolean isOneSa;
	private static String hashkey;
	private JTable table2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Results frame = new Results(isOneSa,hashkey);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Results(boolean isOneSa,String hashkey) {
		super();
		this.isOneSa = isOneSa;
		this.hashkey = hashkey;
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 37, 490, 220);
		contentPane.add(scrollPane);
		

		
		ImportUpdateDB db = new ImportUpdateDB();
		if (isOneSa) {
			ResultSet res = db.getAllResults(hashkey);
			String[] columnNames = {"idResult","id_command","Address","Status","Hostname","Port","Uptime","Insert_time"};
			Object[] data = new Object[8];
			DefaultTableModel model = new DefaultTableModel(null,columnNames);
			table = new JTable(model);
			scrollPane.setViewportView(table);
			try {
				while (res.next()) {
					data[0] = res.getInt(1);
					data[1] = res.getString(2);
					data[2] = res.getString(3);
					data[3] = res.getString(4);
					data[4] = res.getString(5);
					data[5] = res.getString(6);
					data[6] = res.getString(7);
					data[7] = res.getTimestamp(8).toString();
					model.addRow(data);
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			JScrollPane scrollPane2 = new JScrollPane();
			scrollPane2.setBounds(676, 37, 203, 220);
			contentPane.add(scrollPane2);
			res = db.getPeriodic(hashkey);
			String[] columnNames2 = {"IdJob","Period"};
			Object[] data2 = new Object[2];
			DefaultTableModel model2 = new DefaultTableModel(null,columnNames2);
			table2 = new JTable(model2);
			scrollPane2.setViewportView(table2);
			try {
				while (res.next()) {
					data2[0] = res.getString(3);
					data2[1] = res.getInt(4);
					model2.addRow(data2);
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			ResultSet res = db.getAllResults();
			String[] columnNames = {"idResult","id_command","Address","Status","Hostname","Port","Uptime","Insert_time","HashId"};
			Object[] data = new Object[9];
			DefaultTableModel model = new DefaultTableModel(null,columnNames);
			table = new JTable(model);
			scrollPane.setViewportView(table);
			try {
				while (res.next()) {
					data[0] = res.getInt(1);
					data[1] = res.getString(2);
					data[2] = res.getString(3);
					data[3] = res.getString(4);
					data[4] = res.getString(5);
					data[5] = res.getString(6);
					data[6] = res.getString(7);
					data[7] = res.getTimestamp(8).toString();
					data[8] = res.getString(9);
					model.addRow(data);
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
