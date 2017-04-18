package edu.hbue.CPA.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库工具类
 */
public class DBUtils {
	/**
	 * 获取数据库连接
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// mysql方式连接
			// １　加载对应的数据库驱动器，在此之前要引入相应的Jar包
			Class.forName("com.mysql.jdbc.Driver");
			// ２　远程数据库连接串，包括驱动接口类型，ＩＰ，端口，数据库名
			String url = "jdbc:mysql://192.168.128.1:3306/cpa_db?charset=utf-8";
			// ３　数据库登录账号及密码
			String user = "root";
			String password = "123456";
			// ４　创建到数据库的连接
			
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// throw new ServiceException("连接失败，请检查数据库名，IP，端口，账号，密码", e);
			System.out.println("连接失败，请检查数据库名，IP，端口，账号，密码");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// throw new ServiceException("未找到对应的数据库驱动", e);
			System.out.println("未找到对应的数据库驱动");
		}
		return conn;
	}

	// oracle方式连接

	// Class.forName("oracle.jdbc.OracleDriver");
	// String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	// String user = "scott";
	// String password = "tiger";

	/**
	 * 开启事务
	 * 
	 * @param conn
	 */
	public static void beginTransaction(Connection conn) {
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
//			throw new ServiceException("Can not begin transaction", e);
		}
	}

	/**
	 * 提交事务
	 * 
	 * @param conn
	 */
	public static void commit(Connection conn) {
		try {
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
//			throw new ServiceException("Can not commit transaction", e);
		}
	}

	/**
	 * 回滚事务
	 * 
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {
			conn.rollback();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
//			throw new ServiceException("Can not rollback transaction", e);
		}
	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
//			throw new ServiceException("Can not close connection", e);
		}
	}

	/**
	 * 关闭statement
	 * 
	 * @param stmt
	 */
	public static void closeStatement(ResultSet rs, Statement stmt) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
//			throw new ServiceException("Can not close statement", e);
		}
	}
}