package edu.hbue.CPA.GUI;

import edu.hbue.CPA.common.UploadDownloadUtil;
import edu.hbue.CPA.common.resources;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.hbue.CPA.msg.domain.*;
import edu.hbue.CPA.msg.service.*;

import java.awt.Window.Type;
import java.awt.Panel;
import java.awt.Label;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Scrollbar;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Button;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Admin extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String Welcome="welcome";
	private resources res;
	private JTextField SS;
	private JTextField SU;
	private JTable Utable;
	private JTable Stable;
	private UserService Uservice;
	private SoftwareService Sservice;
	private ChangPass cp;
	private EditInsPara EIP;
	private AddUser Au;
	private JScrollPane UscrollPane;
	private JScrollPane SscrollPane;
	private AddSoftware AS;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Admin frame = new Admin();
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
	public Admin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Admin.class.getResource("/resources/drive-cloud-01.png")));
		if(res.luser==null){
			JOptionPane.showMessageDialog(contentPane, "Access exception", "failed",JOptionPane.WARNING_MESSAGE);
			
		}
		Welcome+=":"+res.luser.getUsername();
		setTitle("Admin");
		setType(Type.POPUP);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		setLocationRelativeTo(null);
		Uservice=UserService.getInstance();
		Sservice=SoftwareService.getInstance();
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAccount = new JMenu("Account");
		menuBar.add(mnAccount);
		
		JMenu mnSafe = new JMenu("Safe");
		mnAccount.add(mnSafe);
		
		JMenuItem mntmChangePassword = new JMenuItem("Change Password");
		mntmChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cp =new ChangPass();
				cp.setVisible(true);
			}
		});

		mnSafe.add(mntmChangePassword);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Label label = new Label(Welcome);
		label.setBounds(20, 20, 100, 50);
		contentPane.add(label);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(20, 70, 600, 340);
		contentPane.add(tabbedPane);
		
		JPanel Index = new JPanel();
		tabbedPane.addTab("Index", null, Index, null);
		Index.setLayout(null);
		
		JLabel lblTime = new JLabel("Time:"+res.time);
		lblTime.setBounds(170, 30, 150, 30);
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		Index.add(lblTime);
		
		JLabel lblIpAddress = new JLabel("IP Address:"+res.ip);
		lblIpAddress.setBounds(170, 90, 200, 30);
		lblIpAddress.setHorizontalAlignment(SwingConstants.LEFT);
		Index.add(lblIpAddress);
		
		JLabel lblAmountOfSofts = new JLabel("Amount of Softs:"+Sservice.getSoftwareListRowCount(res.Software));
		lblAmountOfSofts.setBounds(170, 150, 150, 30);
		lblAmountOfSofts.setHorizontalAlignment(SwingConstants.LEFT);
		Index.add(lblAmountOfSofts);
		
		Uservice = UserService.getInstance();
		res.Cuser=new User();
		JLabel lblAmountOfUsers = new JLabel("Amount of Users:"+Uservice.getUserListRowCount(res.Cuser));
		lblAmountOfUsers.setBounds(170, 210, 150, 30);
		lblAmountOfUsers.setHorizontalAlignment(SwingConstants.LEFT);
		Index.add(lblAmountOfUsers);
		
		JLabel lblNote = new JLabel("When you editing the soft,please ban users");
		lblNote.setBounds(170, 270, 100, 30);
		lblNote.setHorizontalAlignment(SwingConstants.LEFT);
		Index.add(lblAmountOfUsers);
		
		Panel UserManager = new Panel();
		tabbedPane.addTab("User Manager", null, UserManager, null);
		UserManager.setLayout(null);
		
		JPanel UPanel = new JPanel();
		UPanel.setBounds(20, 10, 400, 35);
		UserManager.add(UPanel);
		UPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		SU = new JTextField();
		SU.setHorizontalAlignment(SwingConstants.LEFT);
		UPanel.add(SU);
		SU.setColumns(25);
		
		JScrollPane UscrollPane = new JScrollPane();
		UscrollPane.setBounds(20, 55, 400, 245);
		UserManager.add(UscrollPane);
		res.Cuser=new User();
		int i=Uservice.getUserListRowCount(res.Cuser);
		ArrayList<User> UL=Uservice.getUserList(i,res.Cuser);
		Utable = new JTable();
		Object[][] o=new Object[i][3];
		for(int t=0;t<i;t++){
			for(int j=0;j<3;j++){
				if (j==0){
					o[t][j]=UL.get(t).getId();	
				}
				if(j==1){
					o[t][j]=UL.get(t).getUsername();
				}
				if(j==2){
					if(UL.get(t).getRule()==0)
						o[t][j]="client";
					else if(UL.get(t).getRule()==1)
						o[t][j]="admin";	
					else o[t][j]="banned";
				}
			}
		}
		Utable.setModel(new DefaultTableModel(
			o,
			new String[] {
				"ID", "UserName", "Rule"
			}
		));
		UscrollPane.setViewportView(Utable);
		
		JButton btnAddUser = new JButton("Add user");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Au =new AddUser();
				Au.setVisible(true);
			}
		});
		btnAddUser.setBounds(475, 80, 115, 25);
		UserManager.add(btnAddUser);

		JButton btnSearchUser = new JButton("Search User");
		btnSearchUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				res.Cuser=new User();
				res.Cuser.setUsername(SU.getText());
				int i=Uservice.getUserListRowCount(res.Cuser);
				ArrayList<User> UL=Uservice.getUserList(i,res.Cuser);
				Utable = new JTable();
				Object[][] o=new Object[i][3];
				for(int t=0;t<i;t++){
					for(int j=0;j<3;j++){
						if (j==0){
							o[t][j]=UL.get(t).getId();	
						}
						if(j==1){
							o[t][j]=UL.get(t).getUsername();
						}
						if(j==2){
							if(UL.get(t).getRule()==0)
								o[t][j]="client";
							else if(UL.get(t).getRule()==1)
								o[t][j]="admin";
							else o[t][j]="banned";
						}
					}
				}
				Utable.setModel(new DefaultTableModel(
					o,
					new String[] {
						"ID", "UserName", "Rule"
					}
				));
				UscrollPane.setViewportView(Utable);
				
			}
		});
		btnSearchUser.setHorizontalAlignment(SwingConstants.RIGHT);
		UPanel.add(btnSearchUser);
		
		JButton btnVPassword = new JButton("View Password");
		btnVPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		btnVPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int t =Integer.parseInt(String.valueOf( Utable.getValueAt(Utable.getSelectedRow(), 0)));
				res.Cuser=new User();
				res.Cuser.setId(t);
				String P=Uservice.findUser(res.Cuser).getPassword();
				JOptionPane.showMessageDialog(contentPane, "Password is "+P, "View Password",JOptionPane.WARNING_MESSAGE);
			}
		});
		btnVPassword.setBounds(475, 130, 115, 25);
		UserManager.add(btnVPassword);
		
		JButton btnBanUser = new JButton("(Un)Ban User");
		btnBanUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean bl =true;
				res.Cuser=new User();
				res.Cuser.setRule(0);
				int i=Uservice.getUserListRowCount(res.Cuser);
//				System.out.println(i);
				if(i==0){
					res.Cuser.setRule(2);
					bl=false;
					i=Uservice.getUserListRowCount(res.Cuser);
					ArrayList<User> UL=Uservice.getUserList(i,res.Cuser);	
					for(int j=0;j<i;j++){
						UL.get(j).setRule(0);
						try {
							Uservice.updateUser(UL.get(j));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						}
				}else{
					ArrayList<User> UL=Uservice.getUserList(i,res.Cuser);	
					for(int j=0;j<i;j++){
						UL.get(j).setRule(2);
//						int id =UL.get(j).getId();
//						String un=UL.get(j).getUsername();
//						int r =UL.get(j).getRule();
//						System.out.println(id+" "+un+" "+r);
						try {
							Uservice.updateUser(UL.get(j));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
				
			}
				if(bl){
					JOptionPane.showMessageDialog(contentPane, "ALL Users were banned", "Banned",JOptionPane.WARNING_MESSAGE);
				}else {JOptionPane.showMessageDialog(contentPane, "ALL Users were unbanned", "Unbanned",JOptionPane.WARNING_MESSAGE);}
				
				reload();
//				System.out.println("reload");
				res.Cuser=new User();
				res.Cuser.setUsername("");
				i=Uservice.getUserListRowCount(res.Cuser);
				ArrayList<User> UL=Uservice.getUserList(i,res.Cuser);
				Utable = new JTable();
				Object[][] o=new Object[i][3];
				for(int t=0;t<i;t++){
					for(int j=0;j<3;j++){
						if (j==0){
							o[t][j]=UL.get(t).getId();	
						}
						if(j==1){
							o[t][j]=UL.get(t).getUsername();
						}
						if(j==2){
							if(UL.get(t).getRule()==0)
								o[t][j]="client";
							else if(UL.get(t).getRule()==1)
								o[t][j]="admin";
							else o[t][j]="banned";
						}
					}
				}
				Utable.setModel(new DefaultTableModel(
					o,
					new String[] {
						"ID", "UserName", "Rule"
					}
				));
				UscrollPane.setViewportView(Utable);
			}
			});
		btnBanUser.setBounds(475, 180, 115, 25);
		UserManager.add(btnBanUser);
		
		JButton btnDeleteUser = new JButton("Delete User");
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tk[] ={Integer.parseInt(String.valueOf( Utable.getValueAt(Utable.getSelectedRow(), 0)))};	
				if(Uservice.deleteUsers(tk)){
					JOptionPane.showMessageDialog(contentPane, "Delete Success", "Success",JOptionPane.WARNING_MESSAGE);
				}else {JOptionPane.showMessageDialog(contentPane, "Delete Failed", "Failed",JOptionPane.WARNING_MESSAGE);}
				res.Cuser=new User();
				res.Cuser.setUsername("");
				int i=Uservice.getUserListRowCount(res.Cuser);
				ArrayList<User> UL=Uservice.getUserList(i,res.Cuser);
				Utable = new JTable();
				Object[][] o=new Object[i][3];
				for(int t=0;t<i;t++){
					for(int j=0;j<3;j++){
						if (j==0){
							o[t][j]=UL.get(t).getId();	
						}
						if(j==1){
							o[t][j]=UL.get(t).getUsername();
						}
						if(j==2){
							if(UL.get(t).getRule()==0)
								o[t][j]="client";
							else if(UL.get(t).getRule()==1)
								o[t][j]="admin";
							else o[t][j]="banned";
						}
					}
				}
				Utable.setModel(new DefaultTableModel(
					o,
					new String[] {
						"ID", "UserName", "Rule"
					}
				));
				UscrollPane.setViewportView(Utable);
			}
		});
		btnDeleteUser.setBounds(475, 230, 115, 25);
		UserManager.add(btnDeleteUser);
		
		Panel SoftManager = new Panel();
		tabbedPane.addTab("Soft Manager", null, SoftManager, null);
		SoftManager.setLayout(null);
		
		JPanel SPanel = new JPanel();
		SPanel.setBounds(20, 10, 400, 35);
		FlowLayout fl_SPanel = (FlowLayout) SPanel.getLayout();
		fl_SPanel.setHgap(5);
		fl_SPanel.setVgap(5);
		SoftManager.add(SPanel);
		
		SS = new JTextField();
		SPanel.add(SS);
		SS.setColumns(25);
		
		JButton btnSearchSoft = new JButton("Search Soft");
		btnSearchSoft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				res.Software=new Software();
				res.Software.setAppName(SS.getText());
				int s=Sservice.getSoftwareListRowCount(res.Software);
//				System.out.println(s);
				ArrayList<Software> SL=Sservice.getSoftwareList(s,res.Software);
				Object[][] st=new Object[s][3];
				for(int t=0;t<s;t++){
					for(int j=0;j<3;j++){
						if (j==0){
							st[t][j]=SL.get(t).getID();
//							System.out.println(SL.get(t).getID());
						}
						if(j==1){
							st[t][j]=SL.get(t).getAppName();
						}
						if(j==2){
							if(SL.get(t).getAppType()==0)
								st[t][j]="Single File";
							else if(SL.get(t).getAppType()==1)
								st[t][j]="Green Software";	
							else if(SL.get(t).getAppType()==2)	st[t][j]="Slient Software";
							else st[t][j]="Install Software";
						}
					}
				}
				Stable.setModel(new DefaultTableModel(
					st,
					new String[] {
						"ID", "AppName", "AppType"
					}
				));
//				SscrollPane.setViewportView(Stable);
			}
		});
		SPanel.add(btnSearchSoft);
		
		JScrollPane SscrollPane = new JScrollPane();
		SscrollPane.setBounds(20, 55, 400, 245);
		SoftManager.add(SscrollPane);
		
		Stable = new JTable();
		res.Software=new Software();
		int s=Sservice.getSoftwareListRowCount(res.Software);
//		System.out.println(s);
		ArrayList<Software> SL=Sservice.getSoftwareList(s,res.Software);
		Object[][] st=new Object[s][3];
		for(int t=0;t<s;t++){
			for(int j=0;j<3;j++){
				if (j==0){
					st[t][j]=SL.get(t).getID();
//					System.out.println(SL.get(t).getID());
				}
				if(j==1){
					st[t][j]=SL.get(t).getAppName();
				}
				if(j==2){
					if(SL.get(t).getAppType()==0)
						st[t][j]="Single File";
					else if(SL.get(t).getAppType()==1)
						st[t][j]="Green Software";	
					else if(SL.get(t).getAppType()==2)	st[t][j]="Slient Software";
					else st[t][j]="Install Software";
				}
			}
		}
		Stable.setModel(new DefaultTableModel(
			st,
			new String[] {
				"ID", "AppName", "AppType"
			}
		));
		SscrollPane.setViewportView(Stable);
		
		JButton btnAddSoft = new JButton("Add Soft");
		btnAddSoft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AS =new AddSoftware();
				AS.setVisible(true);
			}
		});
		btnAddSoft.setBounds(470, 55, 115, 25);
		SoftManager.add(btnAddSoft);
		
		JButton btnDetails = new JButton("Details");
		btnDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int t =Integer.parseInt(String.valueOf( Stable.getValueAt(Stable.getSelectedRow(), 0)));
				res.Software=new Software();
				res.Software.setID(t);
				int I=Sservice.findSoftware(res.Software).getID();
				String AN=Sservice.findSoftware(res.Software).getAppName();
				String CP=Sservice.findSoftware(res.Software).getCopyPath();
				String IP=Sservice.findSoftware(res.Software).getInsPath();
				int T=Sservice.findSoftware(res.Software).getAppType();
				String IPA=Sservice.findSoftware(res.Software).getInsPara();
				String AT=new String();
				if(T==0)
					AT="Single File";
				else if(T==1)
					AT="Green Software";	
				else if(T==2)	AT="Slient Software";
				else AT="Install Software";
				JOptionPane.showMessageDialog(contentPane, "ID: "+I+"\n App　Name: "+AN+"\n App Type: "+AT+"\n Copy Path: "+CP+"\n Ins Path: "+IP+"\n Ins Para: "+IPA, "View Details",JOptionPane.WARNING_MESSAGE);
			}
		});
		btnDetails.setBounds(470, 105, 115, 25);
		SoftManager.add(btnDetails);
		
		JButton btnEditInstallPara = new JButton("Edit Ins Para");
		btnEditInstallPara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int t =Integer.parseInt(String.valueOf( Stable.getValueAt(Stable.getSelectedRow(), 0)));
				res.Software=new Software();
				res.Software.setID(t);
				res.Software=Sservice.findSoftware(res.Software);
				EIP =new EditInsPara(res.Software);
				EIP.setVisible(true);
				
			}
		});
		btnEditInstallPara.setBounds(470, 155, 115, 25);
		SoftManager.add(btnEditInstallPara);
		
		JButton btnDeleteSoft = new JButton("Delete Soft");
		btnDeleteSoft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tk[] ={Integer.parseInt(String.valueOf( Stable.getValueAt(Stable.getSelectedRow(), 0)))};	
				UploadDownloadUtil UDU = new UploadDownloadUtil();
				res.Software=new Software();
				res.Software.setID(tk[0]);
				res.Software=Sservice.findSoftware(res.Software);
				if(UDU.smbDelete(res.Software.getCopyPath())){
					JOptionPane.showMessageDialog(contentPane, "Delete SMB File Success", "Success",JOptionPane.WARNING_MESSAGE);
				}else {JOptionPane.showMessageDialog(contentPane, "Delete SMB File Failed", "Failed",JOptionPane.WARNING_MESSAGE);}
				if(Sservice.deleteSoftware(tk)){
					JOptionPane.showMessageDialog(contentPane, "Delete Success", "Success",JOptionPane.WARNING_MESSAGE);
				}else {JOptionPane.showMessageDialog(contentPane, "Delete Failed", "Failed",JOptionPane.WARNING_MESSAGE);}
				res.Software=new Software();
				res.Software.setAppName("");
				int s=Sservice.getSoftwareListRowCount(res.Software);
//				System.out.println(s);
				ArrayList<Software> SL=Sservice.getSoftwareList(s,res.Software);
				Object[][] st=new Object[s][3];
				for(int t=0;t<s;t++){
					for(int j=0;j<3;j++){
						if (j==0){
							st[t][j]=SL.get(t).getID();
//							System.out.println(SL.get(t).getID());
						}
						if(j==1){
							st[t][j]=SL.get(t).getAppName();
						}
						if(j==2){
							if(SL.get(t).getAppType()==0)
								st[t][j]="Single File";
							else if(SL.get(t).getAppType()==1)
								st[t][j]="Green Software";	
							else if(SL.get(t).getAppType()==2)	st[t][j]="Slient Software";
							else st[t][j]="Install Software";
						}
					}
				}
				Stable.setModel(new DefaultTableModel(
					st,
					new String[] {
						"ID", "AppName", "AppType"
					}
				));
			}
		});
		btnDeleteSoft.setBounds(470, 205, 115, 25);
		SoftManager.add(btnDeleteSoft);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tabbedPane, UserManager, SoftManager}));
	}

	protected void reload() {
		
		
	}

}
