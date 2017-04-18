package edu.hbue.CPA.msg.Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.hbue.CPA.common.DBUtils;
import edu.hbue.CPA.common.page.Pageable;
import edu.hbue.CPA.common.page.PageableResultSet;
import edu.hbue.CPA.msg.domain.User;

/**
 * 用户表操作类
 */
public class UserDaoImpl implements UserDao {
	/**
	 * 数据库连�?
	 */
	private Connection conn;

	/**
	 * 构�?�方�?
	 * 
	 * @param conn
	 *            数据库连�?
	 */
	public UserDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public int save(User user) throws SQLException {
		// SQL语句
		// String save_sql =
		// "INSERT INTO tab_user VALUES (TAB_USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String save_sql = "INSERT INTO tab_user(username) VALUES " + "(?)";

		PreparedStatement pstmt = null;
		int row = 0;// 记录受影响的记录数，初始为０，假设ＣＲＵＤ操作是不成功的
		int idx = 1;// 要植入的参数�?
		try {
			// 设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(save_sql);
			pstmt.setString(idx, user.getUsername());
			// 注入数据表的只能是int 的相关id，故只需要获取对象的id即可
			// 而对象包装过来时，是整个对象，不能只是id.
			// city,certtype,userType都是�?�?

			// prepareStatement中的setDate(para)入参要的是java.sql.Date
			// 而现系统只能提供java.util.Date
			// �?以二者之间转换，正像�?种字符集向一种字符集转换�?样�??
			row = pstmt.executeUpdate();
		} finally {
			DBUtils.closeStatement(null, pstmt);
		}
		return row;
	}

	@Override
	public int deleteUsers(int[] userIdList) throws SQLException {
		// 构建�?有删除用户字符串，形如[100, 101]-->(100,101)
		String param = Arrays.toString(userIdList).replace("[", "(")
				.replace("]", ")");
		String sql = "DELETE FROM tab_user WHERE id IN " + param;
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
	public User login(String username, String password) throws Exception {
		// SQL语句
		StringBuffer buff = new StringBuffer();
		buff.append("SELECT u.* ");
		buff.append("FROM tab_user  u ");
		buff.append("WHERE username=? AND password = ? ");
		String find_sql = buff.toString();
		
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 设置语句对象，SQL语句条件
			pstmt = conn.prepareStatement(find_sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
//			System.out.println(find_sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// 解析结果集对象，封装查询结果
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRule(rs.getInt("rule"));

				// city
				// CertType
				// UserType
			}
		} finally {
			// System.out.println("udim:" + pstmt);
			DBUtils.closeStatement(rs, pstmt);
		}

		return user;
	}

	@Override
	public User findUser(User one) throws SQLException {
		// SQL语句
		StringBuffer find_sql = new StringBuffer();
		find_sql.append("SELECT u.* ");
		find_sql.append("FROM tab_user  u ");
		find_sql.append("WHERE");

		// 查询条件标记
		boolean tag = false;
		// 查询条件id字段
		Integer id = one.getId();
		if (id != null && id != 0) {
			find_sql.append(" u.id=" + id);
			tag = true;
//			System.out.println(id);
		}
		// 查询条件username字段
		String username = one.getUsername();
		if (username != null && !username.isEmpty()) {
			find_sql.append(" u.username='" + username + "'");
			tag = true;
		}
		// 查询条件password字段
		String password = one.getPassword();
		if (password != null && !password.isEmpty()) {
			find_sql.append(" AND u.password='" + password + "'");
			tag = true;
		}
		// 查询条件rule字段
		Integer rule = one.getRule();
		if (rule != null ) {
			find_sql.append(" AND u.rule='" + rule + "'");
			tag = true;
		}
//		// 查询条件realname字段，模糊查�?
//		String realname = one.getRealname();
//		if (realname != null && !realname.isEmpty()) {
//			find_sql.append(" AND u.realname LIKE '%" + realname + "%'");
//			tag = true;
//		}
//		// 查询条件sex字段
//		String sex = one.getSex();
//		if (sex != null && !sex.isEmpty()) {
//			find_sql.append(" AND u.sex='" + sex + "'");
//			tag = true;
//		}
//		// 查询条件city字段
//		if (one.getCity() != null) {
//			Integer city = one.getCity().getId();
//			if (city != null && city != 0) {
//				find_sql.append(" AND u.city=" + city);
//				tag = true;
//			}
//		}
//		// 查询条件cert_type字段
//		if (one.getCertType() != null) {
//			Integer certtype = one.getCertType().getId();
//			if (certtype != null && certtype != 0) {
//				find_sql.append(" AND u.cert_type=" + certtype);
//				tag = true;
//			}
//		}
//		// 查询条件cert字段
//		String cert = one.getCert();
//		if (cert != null && !cert.isEmpty()) {
//			find_sql.append(" AND u.cert LIKE '%" + cert + "%'");
//			tag = true;
//		}
//		// 查询条件user_type字段
//		if (one.getUserType() != null) {
//			Integer usertype = one.getUserType().getId();
//			if (usertype != null && usertype != 0) {
//				find_sql.append(" AND u.user_type=" + usertype);
//				tag = true;
//			}
//		}
//		// 查询条件content字段
//		String content = one.getContent();
//		if (content != null && !content.isEmpty()) {
//			find_sql.append(" AND u.content LIKE '%" + content + "%'");
//			tag = true;
//		}
//		// 查询条件status字段
//		String status = one.getStatus();
//		if (status != null && !status.isEmpty()) {
//			find_sql.append(" AND u.status='" + status + "'");
//			tag = true;
//		}
//		// 查询条件login_ip字段
//		String ip = one.getLoginIp();
//		if (ip != null && !ip.isEmpty()) {
//			find_sql.append(" AND u.login_ip='" + ip + "'");
//			tag = true;
//		}
//		// 查询条件image_path字段
//		String image = one.getImagePath();
//		if (image != null && !image.isEmpty()) {
//			find_sql.append(" AND u.image_path='" + image + "'");
//			tag = true;
//		}
//		System.out.println(find_sql);

		// 若没有查询条件则返回对象为null
		if (!tag) {
			return null;
		}

		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(find_sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// 解析结果集对象，封装查询结果
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRule(rs.getInt("rule"));
//				user.setRealname(rs.getString("realname"));
//				user.setSex(rs.getString("sex"));
//
//				// city
//				Province province = new Province();
//				province.setId(rs.getInt("pid"));
//				province.setProvince(rs.getString("pprovince"));
//				province.setProvinceId(rs.getString("pprovinceid"));
//
//				City city = new City();
//				city.setId(rs.getInt("cid"));
//				city.setCityId(rs.getString("ccityid"));
//				city.setCity(rs.getString("ccity"));
//				city.setProvince(province);
//
//				user.setCity(city);
//
//				// CertType
//				CertType certType = new CertType();
//				certType.setId(rs.getInt("eid"));
//				certType.setContent(rs.getString("econtent"));
//				user.setCertType(certType);
//
//				user.setCert(rs.getString("cert"));
//				user.setBirthday(rs.getString("birthday"));
//
//				// UserType
//				UserType userType = new UserType();
//				userType.setId(rs.getInt("tid"));
//				userType.setContent(rs.getString("tcontent"));
//				user.setUserType(userType);
//
//				user.setContent(rs.getString("content"));
//				user.setStatus(rs.getString("status"));
//				user.setLoginIp(rs.getString("login_ip"));
//				user.setImagePath(rs.getString("image_path"));
			}
		} finally {
			// System.out.println("udi:finduser"+pstmt);
			DBUtils.closeStatement(rs, pstmt);
		}
		return user;
	}

	/**
	 * 获取用户列表�?大行�?
	 * 
	 * @return 列表�?大行�?
	 * @throws SQLException
	 */
	@Override
	public int getUserListRowCount(User one) throws SQLException {
		int rowCount = 0;
		// SQL语句
		StringBuffer find_sql = new StringBuffer();
		find_sql.append("SELECT count(*) FROM tab_user u ");
//		find_sql.append("FROM tab_user  u, tab_city c, tab_province p, tab_usertype t, tab_certtype e ");
//		find_sql.append("WHERE u.city = c.id AND c.father = p.provinceid AND u.user_type = t.id AND u.cert_type = e.id");
		find_sql.append("where 1=1 ");
		// 查询条件id字段
		Integer id = one.getId();
		if (id != null && id != 0) {
			find_sql.append(" AND u.id=" + id);
		}
		// 查询条件username字段
		String username = one.getUsername();
		if (username != null && !username.isEmpty()) {
			find_sql.append(" AND u.username like '%" + username + "%'");
		}
		// 查询条件password字段
		String password = one.getPassword();
		if (password != null && !password.isEmpty()) {
			find_sql.append(" AND u.password='" + password + "'");
		}
		// 查询条件rule字段
		Integer rule = one.getRule();
		if (rule != null) {
			find_sql.append(" AND u.rule='" + rule + "'");
		}
//		System.out.println("0"+find_sql);
		// 查询条件realname字段，模糊查�?
//		String realname = one.getRealname();
//		if (realname != null && !realname.isEmpty()) {
//			find_sql.append(" AND u.realname LIKE '%" + realname + "%'");
//		}
//		// 查询条件sex字段
//		String sex = one.getSex();
//		if (sex != null && !sex.isEmpty()) {
//			find_sql.append(" AND u.sex='" + sex + "'");
//		}
//		// 查询条件city字段
//		if (one.getCity() != null) {
//			Integer city = one.getCity().getId();
//			if (city != null && city != 0) {
//				find_sql.append(" AND u.city=" + city);
//			}
//		}
//		// 查询条件cert_type字段
//		if (one.getCertType() != null) {
//			Integer certtype = one.getCertType().getId();
//			if (certtype != null && certtype != 0) {
//				find_sql.append(" AND u.cert_type=" + certtype);
//			}
//		}
//		// 查询条件cert字段
//		String cert = one.getCert();
//		if (cert != null && !cert.isEmpty()) {
//			find_sql.append(" AND u.cert LIKE '%" + cert + "%'");
//		}
//		// 查询条件user_type字段
//		if (one.getUserType() != null) {
//			Integer usertype = one.getUserType().getId();
//			if (usertype != null && usertype != 0) {
//				find_sql.append(" AND u.user_type=" + usertype);
//			}
//		}
//		// 查询条件content字段
//		String content = one.getContent();
//		if (content != null && !content.isEmpty()) {
//			find_sql.append(" AND u.content LIKE '%" + content + "%'");
//		}
//		// 查询条件status字段
//		String status = one.getStatus();
//		if (status != null && !status.isEmpty()) {
//			find_sql.append(" AND u.status='" + status + "'");
//		}
//		// 查询条件login_ip字段
//		String ip = one.getLoginIp();
//		if (ip != null && !ip.isEmpty()) {
//			find_sql.append(" AND u.login_ip='" + ip + "'");
//		}
//		// 查询条件image_path字段
//		String image = one.getImagePath();
//		if (image != null && !image.isEmpty()) {
//			find_sql.append(" AND u.image_path='" + image + "'");
//		}

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

	/**
	 * 获取用户列表�?大页�?
	 * 
	 * @param pageSize
	 *            ，每页显示信息条�?
	 * @return 列表�?大页�?
	 * @throws SQLException
	 */
	@Override
	public int getUserListPageCount(int pageSize, User one) throws SQLException {
		int res = 0;

		int rowCount = getUserListRowCount(one);
		if (rowCount % pageSize == 0) {
			res = rowCount / pageSize;
		} else {
			res = rowCount / pageSize + 1;
		}

		return res;
	}

	/**
	 * 获取指定页用户信息列表，通过分页SQL语句实现
	 * 
	 * @param pageSize
	 *            ，每页显示信息条�?
	 * @param pageNo
	 *            ，需要获取的页数
	 * @param one
	 *            ，需要获取的页数
	 * @return 用户信息列表，List[User]，若无满足条件则列表为空
	 * @throws SQLException
	 */
	@Override
	public ArrayList<User> getUserList(int pageSize, int pageNo, User one)
			throws Exception {
		// SQL语句
		StringBuffer find_sql = new StringBuffer();
		find_sql.append("SELECT u.* ");

		find_sql.append("FROM tab_user  u ");
		
		find_sql.append("where 1=1 ");

		// 查询条件id字段
		Integer id = one.getId();
		if (id != null && id != 0) {
			find_sql.append(" AND u.id=" + id);
		}
		// 查询条件username字段
		String username = one.getUsername();
		if (username != null && !username.isEmpty()) {
			find_sql.append(" AND u.username like'%" + username + "%'");
		}
		// 查询条件password字段
		String password = one.getPassword();
		if (password != null && !password.isEmpty()) {
			find_sql.append(" AND u.password='" + password + "'");
		}
		// 查询条件rule字段
		Integer rule = one.getRule();
		if (rule != null) {
			find_sql.append(" AND u.rule='" + rule + "'");
		}

		find_sql.append(" limit " + (pageNo - 1) * pageSize + "," + pageSize);
		// 分页SQL语句,这里可能得重写，因为不是在oracle中，而是在mysql�?
		// String sql = "select * from(select a1.*,rownum rn from ("
		// + find_sql.toString() + ") a1 where rownum<=" + rowNum
		// * pageSize + ")where rn>=" + ((rowNum - 1) * pageSize + 1);

		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> list = new ArrayList<User>();
		try {
			// pstmt = conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(find_sql.toString());// 先查询全�?
//			System.out.println(find_sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 解析结果集对象，封装查询结果
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRule(rs.getInt("rule"));

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
	public List<User> getUserListRS(int pageSize, int pageNum, User one)
			throws SQLException {
		// SQL语句
		StringBuffer find_sql = new StringBuffer();
		find_sql.append("SELECT u.*, ");
		find_sql.append("FROM tab_user  u ");
		
		// 查询条件id字段
		Integer id = one.getId();
		if (id != null && id != 0) {
			find_sql.append(" AND u.id=" + id);
		}
		// 查询条件username字段
		String username = one.getUsername();
		if (username != null && !username.isEmpty()) {
			find_sql.append(" AND u.username='" + username + "'");
		}
		// 查询条件password字段
		String password = one.getPassword();
		if (password != null && !password.isEmpty()) {
			find_sql.append(" AND u.password='" + password + "'");
		}
		// 查询条件rule字段
		Integer rule = one.getRule();
		if (rule != null ) {
			find_sql.append(" AND u.rule='" + rule + "'");
		}
		
		User user = null;
		PreparedStatement pstmt = null;
		// 声明分页工具接口对象
		Pageable rs = null;
		List<User> list = new ArrayList<User>();
		try {
			pstmt = conn.prepareStatement(find_sql.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = new PageableResultSet(pstmt.executeQuery());
			rs.setPageSize(pageSize);
			rs.gotoPage(pageNum);
			for (int i = 0; i < rs.getPageRowsCount(); i++) {
				rs.next();

				// 解析结果集对象，封装查询结果
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRule(rs.getInt("rule"));


				list.add(user);
			}
		} finally {
			DBUtils.closeStatement(rs, pstmt);
		}
		return list;
	}

	@Override
	public boolean deleteUsersProcedure(int[] userIdList) throws SQLException {
		// 构建�?有删除用户字符串，形�?(100, 101)
		String param = Arrays.toString(userIdList).replace("[", "(")
				.replace("]", ")");

		// SQL语句，调用存储过程delBigTab，第�?个参数为表名，第二个参数为删除条件，第三个参数为每N条数据提交一�?
		String sql = "{call delBigTab(?,?,?)}";

		boolean row = false;
		// 调用存储过程�?要声明CallableStatement对象
		CallableStatement cst = null;
		try {
			// 获取执行对象
			cst = conn.prepareCall(sql);
			// 设置第一个参数，表名
			cst.setString(1, "tab_user");
			// 设置第二个参数，删除条件
			cst.setString(2, "id IN " + param);
			// 设置第三个参数，�?100条数据提交一�?
			cst.setInt(3, 100);

			row = cst.execute();
		} finally {
			DBUtils.closeStatement(null, cst);
		}
		return row;
	}

	@Override
	public int updateUser(User one) throws Exception {

		// SQL语句
		StringBuffer update_sql = new StringBuffer("UPDATE tab_user set");
		// 查询条件标记
		boolean tag = false;
		int row = 0;
		// 查询条件id字段
		Integer id = one.getId();
		if (id == null || id == 0) {
			return 0;
		}
		// 查询条件username字段
		String username = one.getUsername();
		if (username != null && !username.isEmpty()) {
			update_sql.append(" username='" + username + "'");
			tag = true;
		}
		// 查询条件password字段
		String password = one.getPassword();
		if (password != null && !password.isEmpty()) {
			update_sql.append(", password='" + password + "'");
			tag = true;
		}
		// 查询条件rule字段
		Integer rule = one.getRule();
		if (rule != null) {
			update_sql.append(", rule='" + rule + "'");
			tag = true;
		}
		
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
	public ArrayList<User> getUserList(int rowNum, User one) throws Exception {
		// TODO Auto-generated method stub
		// SQL语句
				StringBuffer find_sql = new StringBuffer();
				find_sql.append("SELECT u.* ");

				find_sql.append("FROM tab_user  u ");
				
				find_sql.append("where 1=1 ");

				// 查询条件id字段
				Integer id = one.getId();
				if (id != null && id != 0) {
					find_sql.append(" AND u.id=" + id);
				}
				// 查询条件username字段
				String username = one.getUsername();
				if (username != null && !username.isEmpty()) {
					find_sql.append(" AND u.username like'%" + username + "%'");
				}
				// 查询条件password字段
				String password = one.getPassword();
				if (password != null && !password.isEmpty()) {
					find_sql.append(" AND u.password='" + password + "'");
				}
				// 查询条件rule字段
				Integer rule = one.getRule();
				if (rule != null ) {
					find_sql.append(" AND u.rule='" + rule + "'");
				}
				User user = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				ArrayList<User> list = new ArrayList<User>();
				try {
					// pstmt = conn.prepareStatement(sql);
					pstmt = conn.prepareStatement(find_sql.toString());// 先查询全�?
//					System.out.println("1"+find_sql);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						// 解析结果集对象，封装查询结果
						user = new User();
						user.setId(rs.getInt("id"));
						user.setUsername(rs.getString("username"));
						user.setPassword(rs.getString("password"));
						user.setRule(rs.getInt("rule"));

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

}
