package Server.anaptDiktua.graphics;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.json.JSONObject;

import Server.anaptDiktua.Requests.BroadcasterToClient;
import Server.anaptDiktua.import_updateDB.ImportUpdateDB;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.LinkedList;

public class Commands extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static String hashkey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Commands frame = new Commands(hashkey);
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
	public Commands(final String hashkey) {
		super();
		this.hashkey = hashkey;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(85, 88, 274, 61);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnAButton = new JButton("Assign");
		btnAButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] parts = textField.getText().split(",");
				String job_id = parts[0];
				String job_parameteres = parts[1];
				String flag = parts[2];
				String time = parts[3];
				if(!job_id.equals("-1") && !job_parameteres.equals("Stop") && flag.equals("true")) {
					ImportUpdateDB db = new ImportUpdateDB();
					db.importPeriodic(hashkey, job_id,Integer.parseInt(time));
				}


				JSONObject data = new JSONObject();
	        	Collection<String> commands = new LinkedList<String>();
	        	commands.add(textField.getText());
	        	data.put("commands",commands);
	        	BroadcasterToClient.SendToClient(hashkey,data);
			}
		});
		btnAButton.setBounds(85, 156, 117, 25);
		contentPane.add(btnAButton);

		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnNewButton.setBounds(229, 156, 117, 25);
		contentPane.add(btnNewButton);
	}
}
