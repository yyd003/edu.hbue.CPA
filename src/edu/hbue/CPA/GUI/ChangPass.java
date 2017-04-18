package edu.hbue.CPA.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.hbue.CPA.common.resources;
import edu.hbue.CPA.msg.service.UserService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Toolkit;

public class ChangPass extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private resources res;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private UserService Uservice;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ChangPass frame = new ChangPass();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ChangPass() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChangPass.class.getResource("/resources/drive-cloud-01.png")));
		setTitle("Chang Password");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblYourPassWould = new JLabel("Your Pass would  be change");
		panel.add(lblYourPassWould);
		
		JButton btnChange = new JButton("Change");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean a ,b;
//				a=res.luser.getPassword()==String.valueOf(passwordField.getPassword());
//				b=String.valueOf(passwordField_1.getPassword()).equals(String.valueOf(passwordField_2.getPassword());
				if(res.luser.getPassword().equals(String.valueOf(passwordField.getPassword())) && String.valueOf(passwordField_1.getPassword()).equals(String.valueOf(passwordField_2.getPassword()))&& !String.valueOf(passwordField_1.getPassword()).equals(String.valueOf(passwordField.getPassword())))
					{	
						Uservice=UserService.getInstance();
						res.luser.setPassword(String.valueOf(passwordField_1.getPassword()));
						try {
							if(Uservice.updateUser(res.luser)){
							JOptionPane.showMessageDialog(contentPane, "Change success", "Success",JOptionPane.WARNING_MESSAGE);
							}else
							{
								JOptionPane.showMessageDialog(contentPane, "Change failed", "failed",JOptionPane.WARNING_MESSAGE);
							}
						} catch (HeadlessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else 
						JOptionPane.showMessageDialog(contentPane, "The new pass or password you entered is incorrect. Please re-enter it.", "failed",JOptionPane.WARNING_MESSAGE);
//				System.out.println(res.luser.getPassword().equals(String.valueOf(passwordField.getPassword()))+"\n"+res.luser.getPassword()+" "+String.valueOf(passwordField.getPassword()));
//				System.out.println(String.valueOf(passwordField_1.getPassword())==String.valueOf(passwordField.getPassword()));
				 
			}
		});
		contentPane.add(btnChange, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		Box verticalBox = Box.createVerticalBox();
		panel_1.add(verticalBox);
		
		JLabel lblYourUsername = new JLabel("          Your username:"+res.luser.getUsername()+"                ");
		lblYourUsername.setHorizontalAlignment(SwingConstants.LEFT);
		verticalBox.add(lblYourUsername);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);
		
		JLabel lblYourOldPass = new JLabel("Your old Pass:");
		horizontalBox_1.add(lblYourOldPass);
		
		passwordField = new JPasswordField();
		horizontalBox_1.add(passwordField);
		
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		
		JLabel lblNewPass = new JLabel("New Pass:");
		lblNewPass.setHorizontalAlignment(SwingConstants.LEFT);
		horizontalBox.add(lblNewPass);
		
		passwordField_1 = new JPasswordField();
		horizontalBox.add(passwordField_1);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_2);
		
		JLabel label = new JLabel("Re in New Pass:");
		horizontalBox_2.add(label);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		
		passwordField_2 = new JPasswordField();
		horizontalBox_2.add(passwordField_2);
	}

}
