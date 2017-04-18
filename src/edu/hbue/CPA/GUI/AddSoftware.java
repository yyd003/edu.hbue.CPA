package edu.hbue.CPA.GUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import edu.hbue.CPA.common.UploadDownloadUtil;
import edu.hbue.CPA.msg.domain.Software;
import edu.hbue.CPA.msg.service.SoftwareService;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class AddSoftware extends JFrame {

	private JPanel contentPane;
	private JFileChooser jf;
	private JTextField FILEADDRESS;
	private File f;
	private TextField textAN;
	private Choice choice;
	private Software Software;
	private TextField textField;
	private String copypath;
	private TextField textField_1;
	private String IP;
	private JButton btnAddSoftware;
	private UploadDownloadUtil UDU;
	private SoftwareService Sservice;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddSoftware frame = new AddSoftware();
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
	public AddSoftware() {
		setType(Type.POPUP);
		setTitle("Add Software");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddSoftware.class.getResource("/resources/drive-cloud-01.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 290);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		copypath="smb://yyd:138245Aa@192.168.128.1/CloudPlatformApp/Program/";
		textField = new TextField();
		textField_1 = new TextField();
		IP="D://CPA/";
		UDU=new UploadDownloadUtil();
		Sservice = SoftwareService.getInstance();
		
		
		Box verticalBox = Box.createVerticalBox();
		contentPane.add(verticalBox);
		
		JPanel Filecho = new JPanel();
		verticalBox.add(Filecho);
		
		JLabel lblFileInfo = new JLabel("FILE INFO: ");
		Filecho.add(lblFileInfo);
		
		FILEADDRESS = new JTextField();
		FILEADDRESS.setText("FILE ADDRESS");
		FILEADDRESS.setEnabled(false);
		FILEADDRESS.setEditable(false);
		Filecho.add(FILEADDRESS);
		FILEADDRESS.setColumns(45);
		
		textAN = new TextField();
		Button FILECHOOSER = new Button("Choose a File");
		FILECHOOSER.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jf = new JFileChooser();
				jf.showOpenDialog(null);
				jf.setVisible(true);
				f =jf.getSelectedFile();
				String fa=String.valueOf(jf.getCurrentDirectory())+"/"+String.valueOf(jf.getName(f));
				StringBuffer sb = new StringBuffer(fa);
				sb.insert(2, "\\");
				fa= sb.toString();
				
				fa =fa.replace("\\", "/");
				textAN.setText(String.valueOf(jf.getName(f)));
				FILEADDRESS.setText(fa);
				btnAddSoftware.setEnabled(true);
			}
		});
		Filecho.add(FILECHOOSER);
		
		JPanel AppName = new JPanel();
		verticalBox.add(AppName);
		
		Label Appname = new Label("Appname:");
		AppName.add(Appname);
		
		textAN.setEnabled(false);
		textAN.setEditable(false);
		textAN.setText("Appname");
		textAN.setColumns(30);
		AppName.add(textAN);
		
		Panel panel_1 = new Panel();
		verticalBox.add(panel_1);
		
		Label label_1 = new Label("App Type:");
		panel_1.add(label_1);
		
		choice = new Choice();
		choice.add("Single File");
		choice.add("Green Software");
		choice.add("Slient Software");
		choice.add("Install Software");
		choice.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				StringBuffer sb=new StringBuffer(copypath);
				sb.append(String.valueOf(choice.getSelectedItem()));
				String copypath1=sb.toString();
				textField.setText(copypath1);
				
			}
		});
		panel_1.add(choice);
		
		JPanel panel = new JPanel();
		verticalBox.add(panel);
		
		Label label = new Label("Copy path:");
		panel.add(label);
		
		textField.setText(copypath+"Single File/");
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setColumns(70);
		panel.add(textField);
		
		JPanel panel_2 = new JPanel();
		verticalBox.add(panel_2);
		
		Label label_2 = new Label("Install path:");
		panel_2.add(label_2);
		
		textField_1.setText(IP+"Single File/");
		textField_1.setEnabled(false);
		textField_1.setEditable(false);
		textField_1.setColumns(70);
		panel_2.add(textField_1);
		
		JPanel panel_3 = new JPanel();
		verticalBox.add(panel_3);
		
		Label label_3 = new Label("Install Parameter:");
		panel_3.add(label_3);
		
		TextField textField_2 = new TextField();
		textField_2.setColumns(70);
		panel_3.add(textField_2);
		
		JPanel panel_4 = new JPanel();
		verticalBox.add(panel_4);
		
		btnAddSoftware = new JButton("Add Software");
		btnAddSoftware.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Software=new Software();
				Software.setAppName(String.valueOf(textAN.getText()));
				Software.setAppType(choice.getSelectedIndex());
				StringBuffer sb =new StringBuffer(String.valueOf(textField.getText()));
				sb.append(String.valueOf(textAN.getText()));
				Software.setCopyPath(String.valueOf(sb));
				Software.setInsPara(String.valueOf(textField_2.getText()));
				StringBuffer sb1 =new StringBuffer(String.valueOf(textField_1.getText()));
				sb1.append(String.valueOf(textAN.getText()));
				String str= String.valueOf(sb1);
				str=str.replace(".exe", "/");
				str=str.replace(".MSI", "/");
				Software.setInsPath(str);
//				System.out.println(String.valueOf(textField.getText())+"\n"+String.valueOf(FILEADDRESS));
			if(UDU.smbPut(String.valueOf(textField.getText()), String.valueOf(FILEADDRESS.getText()))){
				JOptionPane.showMessageDialog(contentPane, "Upload SMB File Success", "Success",JOptionPane.WARNING_MESSAGE);
			}else {JOptionPane.showMessageDialog(contentPane, "Upload SMB File Failed", "Failed",JOptionPane.WARNING_MESSAGE);}
			if(Sservice.addSoftware(Software)){
				JOptionPane.showMessageDialog(contentPane, "Add Software Success", "Success",JOptionPane.WARNING_MESSAGE);
			}else {JOptionPane.showMessageDialog(contentPane, "Add Software File Failed", "Failed",JOptionPane.WARNING_MESSAGE);}
			
		}});
		btnAddSoftware.setEnabled(false);
		panel_4.add(btnAddSoftware);
//		Filecho.add(jf);
		setLocationRelativeTo(null);
	}
}
