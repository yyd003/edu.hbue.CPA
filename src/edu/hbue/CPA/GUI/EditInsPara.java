package edu.hbue.CPA.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import edu.hbue.CPA.common.resources;
import edu.hbue.CPA.msg.domain.*;
import edu.hbue.CPA.msg.service.*;
import java.awt.Toolkit;

public class EditInsPara extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private SoftwareService Sservice;
	private resources res;
//	private Software one;
	private JButton btnAdd;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					EditInsPara frame = new EditInsPara();
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
	public EditInsPara(Software one) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditInsPara.class.getResource("/resources/drive-cloud-01.png")));
		setTitle("Edit Ins Para");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 108);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		JButton btnEdit = new JButton("Edit");
		Sservice=SoftwareService.getInstance();
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblAddNewUser = new JLabel("Ins Para:");
		panel.add(lblAddNewUser);		
		textField = new JTextField();
		textField.setText(one.getInsPara());
		panel.add(textField);
		textField.setColumns(10);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				one.setInsPara(textField.getText());
				try {
					if(Sservice.updateSoftware(one)){
						JOptionPane.showMessageDialog(contentPane, "Edit success", "success",JOptionPane.WARNING_MESSAGE);
					}else
						JOptionPane.showMessageDialog(contentPane, "Edit failed", "failed",JOptionPane.WARNING_MESSAGE);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnEdit, BorderLayout.SOUTH);
	}

}
