package Server.anaptDiktua.graphics;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.Component;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JTextPane;

import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Container;
import java.awt.Window.Type;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

import javax.swing.JPasswordField;

import Server.anaptDiktua.import_updateDB.ImportUpdateDB;

import javax.swing.JTextArea;
import javax.swing.JSeparator;

public class Login extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_1;
	private JTextPane txtpnUsername;

	private Panel panel;
	private String user = null;
	private char[] pass = null;
	private JPasswordField passwordField;
	public Login() {
		
		setPreferredSize(new Dimension(450, 300));
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setFont(new Font("Utopia", Font.BOLD, 13));
		setBackground(SystemColor.controlLtHighlight);
		setForeground(SystemColor.controlText);
		setTitle("Aggregation Manager");
		Container test = getContentPane();
		getContentPane().setFont(new Font("Droid Sans", Font.BOLD, 13));
		getContentPane().setBackground(SystemColor.controlLtHighlight);
		getContentPane().setLayout(null);
		
		panel = new Panel();
		panel.setBounds(0, 0, 450, 300);
		getContentPane().add(panel);
		panel.setLayout(null);

		
		txtpnUsername = new JTextPane();
		txtpnUsername.setBounds(54, 83, 80, 19);
		panel.add(txtpnUsername);
		txtpnUsername.setFont(new Font("Droid Sans", Font.BOLD, 14));
		txtpnUsername.setBackground(SystemColor.controlLtHighlight);
		txtpnUsername.setText("Username");
		txtpnUsername.setEditable(false);
		
		textField_1 = new JTextField();
		textField_1.setBounds(166, 83, 177, 19);
		panel.add(textField_1);
		
		textField_1.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(166, 114, 177, 19);
		passwordField.setEchoChar('*');
		panel.add(passwordField);
		

		
		JTextPane txtpnPassword = new JTextPane();
		txtpnPassword.setBounds(54, 114, 100, 19);
		panel.add(txtpnPassword);
		txtpnPassword.setForeground(SystemColor.textHighlight);
		txtpnPassword.setText("Password");
		txtpnPassword.setFont(new Font("Droid Sans", Font.BOLD, 14));
		txtpnPassword.setEditable(false);
		txtpnPassword.setBackground(Color.WHITE);
		
		final JTextArea txtrWrongUsernameOrand = new JTextArea();
		txtrWrongUsernameOrand.setFont(new Font("Droid Sans", Font.BOLD, 12));
		txtrWrongUsernameOrand.setForeground(Color.RED);
		txtrWrongUsernameOrand.setText("Wrong Username OR/AND Password");
		txtrWrongUsernameOrand.setBounds(88, 38, 304, 19);
		txtrWrongUsernameOrand.setVisible(false);
		panel.add(txtrWrongUsernameOrand);
		txtrWrongUsernameOrand.setVisible(false);
		JButton LoginButton = new JButton("Login");
		LoginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				user = textField_1.getText();
				int success ;
				String myPass=String.valueOf(passwordField.getPassword());		
				System.out.println("username is " + user);
				System.out.printf("password is %s\n",myPass);
				ImportUpdateDB dbObj = new ImportUpdateDB();
				success = dbObj.adminLogin(user, myPass)	;
				if (success !=1){
					txtrWrongUsernameOrand.setVisible(true);
				}
				else {
					txtrWrongUsernameOrand.setVisible(false);
					Dashboard dash = new Dashboard();
					dispose();
				}
			}
		});
		LoginButton.setBounds(270, 145, 73, 25);
		panel.add(LoginButton);

		

		System.out.println("Telos tou Login GUI");
	

	};
	public void runLogin(){
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            pack();
	            setLocationRelativeTo(null);
	            setVisible(true);
	        }
	    });
	}
}
