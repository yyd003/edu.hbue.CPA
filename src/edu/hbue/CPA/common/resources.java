package edu.hbue.CPA.common;

import edu.hbue.CPA.msg.domain.*;
import java.util.Date;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

public class resources {
	public static User luser;
	public static String time;
	public static String ip;
	public static User Cuser;
	public static Software Software;
	public resources(){
		Cuser=new User();
		Software=new Software();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	time=df.format(new Date());
	try {
		ip = InetAddress.getLocalHost().getHostAddress();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//获得本机IP  

	}
}
