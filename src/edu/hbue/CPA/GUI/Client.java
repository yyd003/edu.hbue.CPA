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
import java.io.File;
import java.util.ArrayList;

public class Client extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String Welcome="welcome";
	private resources res;
	private JTextField SS;
	private JTable Stable;
	private UserService Uservice;
	private SoftwareService Sservice;
	private ChangPass cp;
	private EditInsPara EIP;
	private AddUser Au;
	private JScrollPane UscrollPane;
	private JScrollPane SscrollPane;
	private UploadDownloadUtil UDU;

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
	public Client() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Client.class.getResource("/resources/drive-cloud-01.png")));
		if(res.luser==null){
			JOptionPane.showMessageDialog(contentPane, "Access exception", "failed",JOptionPane.WARNING_MESSAGE);
			
		}
		Welcome+=":"+res.luser.getUsername();
		setTitle("Client");
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
		res.Cuser=new User();
		int i=Uservice.getUserListRowCount(res.Cuser);
		ArrayList<User> UL=Uservice.getUserList(i,res.Cuser);
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
		
		JButton btnInsSoft = new JButton("Install Soft");
		btnInsSoft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int t =Integer.parseInt(String.valueOf( Stable.getValueAt(Stable.getSelectedRow(), 0)));
				res.Software=new Software();
				res.Software.setID(t);
				res.Software=Sservice.findSoftware(res.Software);
				UDU=new UploadDownloadUtil();
				String[] s={res.Software.getCopyPath(), resources.Software.getInsPath()};
//				System.out.println(s[0]+"\n"+s[1]);
				UDU.makeDirs(s[1]);
				if(UDU.smbGet(s[0],s[1])){
					JOptionPane.showMessageDialog(contentPane, "Download SMB File Success", "Success",JOptionPane.WARNING_MESSAGE);
				}else JOptionPane.showMessageDialog(contentPane, "Download SMB File Failed", "Failed",JOptionPane.WARNING_MESSAGE);	
				try {
//					System.out.println("cmd /c a"+res.Software.getAppName()+" "+res.Software.getInsPara());
					Runtime.getRuntime().exec("cmd /c "+res.Software.getAppName()+" "+res.Software.getInsPara(), null, new File(res.Software.getInsPath()));		
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(contentPane, e1, "Runtime Exception",JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnInsSoft.setBounds(470, 55, 115, 25);
		SoftManager.add(btnInsSoft);
		
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
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tabbedPane, SoftManager}));
	}

	protected void reload() {
		
		
	}

}
