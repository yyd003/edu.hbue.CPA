package edu.hbue.CPA.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.hbue.CPA.common.resources;
import edu.hbue.CPA.msg.domain.User;
import edu.hbue.CPA.msg.service.UserService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class AddUser extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private UserService Uservice;
	private JLabel label;
	private resources res;
	private User user;
	private JButton btnAdd;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddUser frame = new AddUser();
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
	public AddUser(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddUser.class.getResource("/resources/drive-cloud-01.png")));
		setTitle("Add User");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 135);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		Uservice=UserService.getInstance();
		JButton btnAdd = new JButton("Add");
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblAddNewUser = new JLabel("Add New User:");
		panel.add(lblAddNewUser);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter(){
//			@Override
//			public void keyTyped(KeyEvent e) {
//				
//			}
			@Override
			public void keyReleased(KeyEvent e){
				user=new User();
				user.setUsername(textField.getText());
				String Str;
				if(Uservice.findUser(user)==null){
					Str ="false";
					btnAdd.setEnabled(true);
				}
				else {
				Str="true"; 
				btnAdd.setEnabled(false);
				}
				label.setText(Str);
			}
		});
		panel.add(textField);
		textField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		JLabel lblTheUserIs = new JLabel("The user is exixt:");
		panel_1.add(lblTheUserIs);
		
		label = new JLabel("");
		panel_1.add(label);
		
		btnAdd.setEnabled(false);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Uservice.addUser(user)){
					JOptionPane.showMessageDialog(contentPane, "Add success", "success",JOptionPane.WARNING_MESSAGE);
				}else
					JOptionPane.showMessageDialog(contentPane, "Add failed", "failed",JOptionPane.WARNING_MESSAGE);
			}
		});
		contentPane.add(btnAdd, BorderLayout.SOUTH);
	}

}
