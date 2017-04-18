package edu.hbue.CPA.msg.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import edu.hbue.CPA.common.DBUtils;
import edu.hbue.CPA.msg.domain.Software;
import edu.hbue.CPA.msg.domain.User;

public class SoftwareDaoImpl implements SoftwareDao {
	
	private Connection conn;
	
	public SoftwareDaoImpl(Connection conn) throws SQLException{
		this.conn = conn;
	}

	@Override
	public int save(Software Software) throws Exception {
		// TODO Auto-generated method stub
		String save_sql = "INSERT INTO tab_software(app_name,copy_path,install_path,app_type,ins_para) VALUES (?,?,?,?,?)";
		
		PreparedStatement pstmt = null;
		int row = 0;// 记录受影响的记录数，初始为０，假设ＣＲＵＤ操作是不成功的
		int idx = 0;// 要植入的参数�?
		try {
			// 设置语句对象，SQL语句条件
						pstmt = conn.prepareStatement(save_sql);
						pstmt.setString(++idx, Software.getAppName());
						// 注入数据表的只能是int 的相关id，故只需要获取对象的id即可
						// 而对象包装过来时，是整个对象，不能只是id.
						// city,certtype,userType都是�?�?
						pstmt.setString(++idx, Software.getCopyPath());
						pstmt.setString(++idx, Software.getInsPath());
						pstmt.setString(++idx, String.valueOf(Software.getAppType()));
						pstmt.setString(++idx, Software.getInsPara());
						row = pstmt.executeUpdate();
//						System.out.println(save_sql);
		}finally {
			DBUtils.closeStatement(null, pstmt);
		}
		return row;
	}

	@Override
	public int deleteSoftware(int[] userIdList) throws SQLException {
		// TODO Auto-generated method stub
		String param = Arrays.toString(userIdList).replace("[", "(")
				.replace("]", ")");
		String sql = "DELETE FROM tab_Software WHERE id IN " + param;
		PreparedStatement pstmt = null;
		int row = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			row = pstmt.executeUpdate();
		} finally {
			// System.out.println("udi:deleteusers" + pstmt);
			DBUtils.closeStatement(null, pstmt);
		}
		return row;
	}

	@Override
	public int getSoftwareListRowCount(Software one) throws SQLException {
		// TODO Auto-generated method stub
		int rowCount = 0;
		// SQL语句
		StringBuffer find_sql = new StringBuffer();
		find_sql.append("SELECT count(*) FROM tab_software u ");
//		find_sql.append("FROM tab_user  u, tab_city c, tab_province p, tab_usertype t, tab_certtype e ");
//		find_sql.append("WHERE u.city = c.id AND c.father = p.provinceid AND u.user_type = t.id AND u.cert_type = e.id");
		find_sql.append("where 1=1 ");
		// 查询条件id字段
		Integer id = one.getAppType();
		if (id != null && id!=0) {
			find_sql.append(" AND u.id=" + id);
		}
		// 查询条件username字段
		String username = one.getAppName();
		if (username != null && !username.isEmpty()) {
			find_sql.append(" AND u.APP_NAME like '%" + username + "%'");
		}
		// 查询条件password字段
//		String password = one.getPassword();
//		if (password != null && !password.isEmpty()) {
//			find_sql.append(" AND u.password='" + password + "'");
//		}
		// 查询条件rule字段
//		Integer rule = one.getRule();
//		if (rule != null) {
//			find_sql.append(" AND u.rule='" + rule + "'");
//		}
//		System.out.println("0"+find_sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(find_sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// 根据每页条数计算列表总页�?
				rowCount = rs.getInt(1);
			}
		} finally {
			DBUtils.closeStatement(rs, pstmt);
		}

		return rowCount;
	}

	@Override
	public int updateSoftware(Software one) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer update_sql = new StringBuffer("UPDATE tab_software set");
		// 查询条件标记
		boolean tag = false;
		int row = 0;
		// 查询条件id字段
		Integer id = one.getID();
		if (id == null || id == 0) {
			return 0;
		}
		// 查询条件username字段
		String username = one.getAppName();
		if (username != null && !username.isEmpty()) {
			update_sql.append(" App_Name='" + username + "'");
			tag = true;
		}
		// 查询条件password字段
		String password = one.getInsPara();
		if (password != null ) {
			update_sql.append(", Ins_Para='" + password + "'");
			tag = true;
		}
		// 查询条件rule字段
//		Integer rule = one.getRule();
//		if (rule != null) {
//			update_sql.append(", rule='" + rule + "'");
//			tag = true;
//		}
		
		update_sql.append(" where id=" + id);

//		System.out.println(update_sql);
		// 若没有查询条件则返回对象为null
		if (!tag) {
			return 0;
		}

		PreparedStatement pstmt = null;
		try {
			// 设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(update_sql.toString());
			row = pstmt.executeUpdate();

		} finally {
			// System.out.println("udi:updateuser:" + pstmt);
			DBUtils.closeStatement(null, pstmt);
		}
		return row;
	}

	@Override
	public ArrayList<Software> getSoftwareList(int rowNum, Software one) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer find_sql = new StringBuffer();
		find_sql.append("SELECT u.* ");

		find_sql.append("FROM tab_software  u ");
		
		find_sql.append("where 1=1 ");
		// 查询条件id字段
		Integer id = one.getID();
		if (id != null && id != 0) {
			find_sql.append(" AND u.id=" + id);
		}
		// 查询条件username字段
		String username = one.getAppName();
		if (username != null && !username.isEmpty()) {
			find_sql.append(" AND u.APP_NAME like'%" + username + "%'");
		}
		Software user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Software> list = new ArrayList<Software>();
		try {
			// pstmt = conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(find_sql.toString());// 先查询全�?
//			System.out.println("1"+find_sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 解析结果集对象，封装查询结果
				user = new Software();
				user.setID(rs.getInt("id"));
//				System.out.println(user.getID());
//				System.out.println(rs.getString("APP_NAME"));
				user.setAppName(rs.getString("APP_NAME"));
//				System.out.println(rs.getInt("APP_type"));
				user.setAppType(rs.getInt("APP_type"));
//				System.out.println(rs.getString("COPY_PATH"));
				user.setCopyPath(rs.getString("COPY_PATH"));
//				System.out.println(rs.getString("Ins_para"));
				user.setInsPara(rs.getString("Ins_para"));
				user.setInsPath(rs.getString("install_Path"));

				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			// System.out.println("udi:getuserlist:" + pstmt);
			DBUtils.closeStatement(rs, pstmt);
		}
		return list;
	}

	@Override
	public Software findSoftware(Software one) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer find_sql = new StringBuffer();
		find_sql.append("SELECT u.* ");
		find_sql.append("FROM tab_software  u ");
		find_sql.append("WHERE");

		// 查询条件标记
		boolean tag = false;
		// 查询条件id字段
		Integer id = one.getID();
		if (id != null && id != 0) {
			find_sql.append(" u.id=" + id);
			tag = true;
//			System.out.println(id);
		}
		// 查询条件username字段
		String username = one.getAppName();
		if (username != null && !username.isEmpty()) {
			find_sql.append(" u.App_name='" + username + "'");
			tag = true;
		}
		// 查询条件password字段
//		String password = one.getPassword();
//		if (password != null && !password.isEmpty()) {
//			find_sql.append(" AND u.password='" + password + "'");
//			tag = true;
//		}
		// 查询条件rule字段
//		Integer rule = one.getRule();
//		if (rule != null ) {
//			find_sql.append(" AND u.rule='" + rule + "'");
//			tag = true;
//		}
		if (!tag) {
//			System.out.print(tag);
			return null;
		}
			Software user = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				pstmt = conn.prepareStatement(find_sql.toString());
				rs = pstmt.executeQuery();
//				System.out.println(rs);
//				System.out.println(find_sql);
				if (rs.next()) {
					// 解析结果集对象，封装查询结果
					user = new Software();
//					System.out.println(user.getID());
//					System.out.println(rs.getInt("id"));
					user.setID(rs.getInt("id"));
//					System.out.println(user.getID());
//					System.out.println(rs.getString("APP_NAME"));
					user.setAppName(rs.getString("APP_NAME"));
//					System.out.println(rs.getInt("APP_type"));
					user.setAppType(rs.getInt("APP_type"));
//					System.out.println(rs.getString("COPY_PATH"));
					user.setCopyPath(rs.getString("COPY_PATH"));
//					System.out.println(rs.getString("Ins_para"));
					user.setInsPara(rs.getString("Ins_para"));
					user.setInsPath(rs.getString("install_Path"));
//					System.out.println(user);
					
					
		}
	} finally {
//		 System.out.println("udi:finduser"+pstmt);
		DBUtils.closeStatement(rs, pstmt);
	}
			
	return user;
}

		

}
