package edu.hbue.CPA.GUI;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import edu.hbue.CPA.msg.service.*;

import edu.hbue.CPA.common.resources;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Window.Type;


public class Login {

	private JFrame frmCpa;
	private JTextField user;
	private JPasswordField pass;
	private JOptionPane jPanel;
	JButton btnEnter;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmCpa.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCpa = new JFrame();
		frmCpa.setType(Type.POPUP);
		frmCpa.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/resources/drive-cloud-01.png")));
		frmCpa.setTitle("CPA");
		frmCpa.setForeground(Color.LIGHT_GRAY);
		frmCpa.setBackground(Color.LIGHT_GRAY);
		frmCpa.setBounds(800, 400, 400, 300);
		frmCpa.setLocationRelativeTo(null);
		frmCpa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCpa.getContentPane().setLayout(null);
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setFont(new Font("����Ҧ��", Font.ITALIC, 40));
		lblLogin.setBounds(140, 50, 130, 55);
		frmCpa.getContentPane().add(lblLogin);
		
		JLabel lblUser = new JLabel("USERNAME");
		lblUser.setFont(new Font("����", Font.PLAIN, 19));
		lblUser.setBounds(65, 130, 110, 35);
		frmCpa.getContentPane().add(lblUser);
		
		user = new JTextField();	
		user.requestFocus();
		user.setFont(new Font("����", Font.PLAIN, 19));
		user.setBounds(180, 130, 120, 35);
		frmCpa.getContentPane().add(user);
		user.setColumns(10);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("����", Font.PLAIN, 19));
		lblPassword.setBounds(65, 180, 115, 35);
		frmCpa.getContentPane().add(lblPassword);
		
		pass = new JPasswordField();
		pass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER)
					try {
						login();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		pass.setBounds(180, 180, 120, 35);
		frmCpa.getContentPane().add(pass);
		
		btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					login();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEnter.setBounds(140, 228, 93, 23);
		frmCpa.getContentPane().add(btnEnter);
		frmCpa.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{user, pass, btnEnter}));
	}
	
	public void login() throws Exception{
		UserService service = UserService.getInstance();
		resources res = new resources();
		res.luser = service.login(user.getText(), String.valueOf(pass.getPassword()));
		if(res.luser==null)
			JOptionPane.showMessageDialog(jPanel, "The account name or password you entered is incorrect. Please re-enter it.", "failed",JOptionPane.WARNING_MESSAGE);  			
		else{
				if(res.luser.getRule()==1)
		{
			this.frmCpa.setVisible(false);
			Admin a = new Admin();
			a.setVisible(true);
			btnEnter.setText("Logined");
			btnEnter.setEnabled(false);
			pass.setEnabled(false);
			user.setEnabled(false);
//			System.exit(0);
			a.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					btnEnter.setText("Enter");
					btnEnter.setEnabled(true);
					pass.setText("");
					user.setText(null);
					pass.setEnabled(true);
					user.setEnabled(true);
					frmCpa.setVisible(true);
				}
			});
			
		}
				else if(res.luser.getRule()==0)
		{
			this.frmCpa.setVisible(false);
			Client c = new Client() ;
			c.setVisible(true);
			
			btnEnter.setText("Logined");
			btnEnter.setEnabled(false);
			pass.setEnabled(false);
			user.setEnabled(false);
//			System.exit(0);
			c.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					btnEnter.setText("Enter");
					btnEnter.setEnabled(true);
					pass.setText("");
					user.setText(null);
					pass.setEnabled(true);
					user.setEnabled(true);
					frmCpa.setVisible(true);
				}
			});
			
		}else{
			JOptionPane.showMessageDialog(jPanel, "The account you entered is unavailable. Please contact the administrator.", "unavailable",JOptionPane.WARNING_MESSAGE); 
		} }
	}
}
