package edu.hbue.CPA.msg.test;

import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.hbue.CPA.msg.domain.*;
import edu.hbue.CPA.msg.service.SoftwareService;

public class TestSoftwareServices {
	SoftwareService service = SoftwareService.getInstance();
	static Process p;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestSoftwareServices t = new TestSoftwareServices();
//		t.testAddSoftware();
//		t.tesdel();
//		t.testUP();
			try {
//				p=Runtime.getRuntime().exec("cmd cmd.exe /t:0f", null, new File("d:\\CPA\\Single File\\cmd"));
				Desktop.getDesktop().open(new File("d:\\CPA\\Single File\\cmd\\cmd.exe /t:0g"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(new JFrame(), e, "Runtime Exception",JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
			}
			
	}
	private void testUP() {
		// TODO Auto-generated method stub
		Software user = new Software();
		// 将用户id为25(测试用户一定要在数据表中)的sex修改为"0"
		// 一定要设置用户的ＩＤ,不然操作不成功
		// 因为：：如果对象没有封装ＩＤ，ＳＱＬ语句就不知道要操作哪条记录，
		// 以致会对整个表相应字段修改成同样的内容,这个是不允许的。

		user.setID(1);
		Software fu = new Software();
		fu = service.findSoftware(user);
//		System.out.println(fu);
				fu.setInsPara("usertt");
		fu.setID(user.getID());
		// fu.setUsername("111");
		try {
			System.out.println("修改用户："+service.updateSoftware(fu));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void tesdel() {
		// TODO Auto-generated method stub
		int[] userIdList = { 2 };
		System.out.println("删除用户："+service.deleteSoftware(userIdList));
	}
	private void testAddSoftware() {
		// TODO Auto-generated method stub
		Software Software = new Software();
		Software.setAppName("test");
		Software.setAppType(0);
		Software.setCopyPath("test");
		Software.setInsPara("");
		Software.setInsPath("test");
		System.out.println("添加软件"+service.addSoftware(Software));
	}

}
