package Server.anaptDiktua.graphics;

import java.awt.EventQueue;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import Server.anaptDiktua.Requests.ReceiveSoftwareAgents;
import Server.anaptDiktua.Requests.RequestsForNmapJobsService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dashboard {

	private JFrame frame;
	private JTable table;
	private JTable table2;
	private DefaultTableModel model;
	private DefaultTableModel model2;
	private final static int def = 60000;
	public final static int INTERVAL = 1000;
	Map<String,ArrayList<String>> SAmap;
	Map<String, Long> OnOffClients;
	private JButton btnShowHistory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard window = new Dashboard();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Dashboard() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 485);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		SAmap = ReceiveSoftwareAgents.getSAmap();
		OnOffClients = RequestsForNmapJobsService.getClientsOnOff();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 31, 400, 211);
		frame.getContentPane().add(scrollPane);
		String[] columnNames = {"idSoftware_Agent",
                "DeviceName",
                "IP",
                "MacAddr",
                "OsVersion",
                "NmapVersion"};
		Object[][] data = new Object[SAmap.size()][6];
		int row = 0;
		for(String elem : SAmap.keySet()) {
			data[row][0] = elem;
			ArrayList<String> arr = SAmap.get(elem);
			data[row][1] = arr.get(0);
			data[row][2] = arr.get(1);
			data[row][3] = arr.get(2);
			data[row][4] = arr.get(3);
			data[row][5] = arr.get(4);
			row++;
		}
        model = new DefaultTableModel(data,columnNames);
        table = new JTable(model);
        Object[] but1 = new Object[SAmap.size()];
        Object[] but2 = new Object[SAmap.size()];
        for (int i=0;i<SAmap.size();i++) {
        	but1[i] = "A";
        	but2[i] = "D";
        }
        model.addColumn("Accept",but1);
        model.addColumn("Deny",but2);
        table.getColumn("Accept").setCellRenderer(new ButtonRenderer());
        table.getColumn("Deny").setCellRenderer(new ButtonRenderer());
        table.getColumn("Accept").setCellEditor(new AccEditor(new JCheckBox()));
        table.getColumn("Deny").setCellEditor(new AccEditor(new JCheckBox()));
		scrollPane.setViewportView(table);






		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(510, 31, 603, 211);
		frame.getContentPane().add(scrollPane2);

		String[] columnNames2 = {"idSoftware_Agent",
                "Status"};
		Object[][] data2 = new Object[OnOffClients.size()][2];
		int row2 = 0;
		for(String elem : OnOffClients.keySet()) {
			data[row2][0] = elem;
			if (System.currentTimeMillis()-OnOffClients.get(elem) > 3*def)
				data[row2][1] = "offline";
			else
				data[row2][1] = "online";
			row2++;
		}
        model2 = new DefaultTableModel(data2,columnNames2);
        table2 = new JTable(model2);
        Object[] but3 = new Object[OnOffClients.size()];
        Object[] but4 = new Object[OnOffClients.size()];
        MyRenderer myRenderer = new MyRenderer();
        for (int i=0;i<OnOffClients.size();i++) {
        	but3[i] = "Assign a job";
        	but4[i] = "Show results";
        }
        model2.addColumn("Tasks",but3);
        model2.addColumn("Results",but4);
        table2.setDefaultRenderer(Object.class, myRenderer);
        table2.getColumn("Tasks").setCellRenderer(new ButtonRenderer());
        table2.getColumn("Results").setCellRenderer(new ButtonRenderer());
        table2.getColumn("Tasks").setCellEditor(new ButtonEditor(new JCheckBox()));
        table2.getColumn("Results").setCellEditor(new ButtonEditor(new JCheckBox()));
		scrollPane2.setViewportView(table2);

		btnShowHistory = new JButton("Show History");
		btnShowHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  Results res = new Results(false,null);
				  res.setVisible(true);
			}
		});
		btnShowHistory.setBounds(689, 255, 195, 25);
		frame.getContentPane().add(btnShowHistory);


		Timer timer = new Timer(INTERVAL, new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
				SAmap = ReceiveSoftwareAgents.getSAmap();
				OnOffClients = RequestsForNmapJobsService.getClientsOnOff();

				model.setRowCount(0);
				Object[] data = new Object[8];
				for(String elem : SAmap.keySet()) {
					data[0] = elem;
					ArrayList<String> arr = SAmap.get(elem);
					data[1] = arr.get(0);
					data[2] = arr.get(1);
					data[3] = arr.get(2);
					data[4] = arr.get(3);
					data[5] = arr.get(4);
					data[6] = "Y";
					data[7] = "N";
					model.addRow(data);
			        table.getColumn("Accept").setCellRenderer(new ButtonRenderer());
			        table.getColumn("Deny").setCellRenderer(new ButtonRenderer());
			        table.getColumn("Accept").setCellEditor(new AccEditor(new JCheckBox()));
			        table.getColumn("Deny").setCellEditor(new AccEditor(new JCheckBox()));
				}


				model2.setRowCount(0);
				Object[] data2 = new Object[4];
				for(String elem : OnOffClients.keySet()) {
					data2[0] = elem;
					if (System.currentTimeMillis()-OnOffClients.get(elem) > 3*def)
						data2[1] = "offline";
					else
						data2[1] = "online";
					System.out.println("Time : " + OnOffClients.get(elem));
					data2[2] = "Assign a Job";
					data2[3] = "Show results";
					model2.addRow(data2);
					MyRenderer myRenderer = new MyRenderer();
					table2.setDefaultRenderer(Object.class, myRenderer);
			        table2.getColumn("Tasks").setCellRenderer(new ButtonRenderer());
			        table2.getColumn("Results").setCellRenderer(new ButtonRenderer());
			        table2.getColumn("Tasks").setCellEditor(new ButtonEditor(new JCheckBox()));
			        table2.getColumn("Results").setCellEditor(new ButtonEditor(new JCheckBox()));
				}

		    }
		});
		timer.start();
	}
}
