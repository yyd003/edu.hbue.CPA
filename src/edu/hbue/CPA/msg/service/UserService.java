package edu.hbue.CPA.msg.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.hbue.CPA.common.DBUtils;
import edu.hbue.CPA.msg.Dao.*;
import edu.hbue.CPA.msg.domain.*;

/**
 * 用户服务类（采用单例模式实现）
 */
public class UserService {

	/**
	 * 类实例
	 */
	private static final UserService instance = new UserService();

	/**
	 * 取得实例
	 * 
	 * @return 实例对象
	 */
	public static UserService getInstance() {
		return instance;
	}

	/**
	 * 构造方法
	 */
	private UserService() {
	}

	/**
	 * 增加用户
	 * 
	 * @param user
	 *            用户对象
	 */
	public boolean addUser(User user) {
		boolean f = false;// 返回添加成功与否的标志，初始不成功
		Connection conn = null;
		try {
			conn = edu.hbue.CPA.common.DBUtils.getConnection();
			UserDao userDao = new UserDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			if (userDao.save(user) > 0) {
				f = true;// 表示添加成功
			}
			DBUtils.commit(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			DBUtils.rollback(conn);
//			throw new ServiceException("添加用户错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
		return f;
	}

	/**
	 * 登陆验证
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return user 用户信息实体
	 * @throws Exception
	 */
	public User login(String username, String password) throws Exception {
		User user = null;
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			UserDao userDao = new UserDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			user = userDao.login(username, password);
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
//			throw new ServiceException("查询用户错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
		return user;
	}

	/**
	 * 删除用户(用IN语句)
	 * 
	 * @param userIdList
	 *            用户ID集合
	 */
	public boolean deleteUsers(int[] userIdList) {
		boolean f = false;
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			UserDao userDao = new UserDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			if (userDao.deleteUsers(userIdList) > 0) {
				f = true;
			}
			DBUtils.commit(conn);
		} catch (Exception e) {
			DBUtils.rollback(conn);
//			throw new ServiceException("删除用户错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
		return f;
	}

	/**
	 * 删除用户(调用存储过程)
	 * 
	 * @param userIdList
	 *            用户ID集合
	 */
	 public void deleteUsersProcedure(int[] userIdList) {
	 Connection conn = null;
	 try {
	 conn = DBUtils.getConnection();
	 UserDao userDao = new UserDaoImpl(conn);
	 DBUtils.beginTransaction(conn);
	 userDao.deleteUsersProcedure(userIdList);
	 DBUtils.commit(conn);
	 } catch (Exception e) {
	 DBUtils.rollback(conn);
//	 throw new ServiceException("删除用户错误", e);
	 } finally {
	 DBUtils.closeConnection(conn);
	 }
	 }

	/**
	 * 通过用户信息查询用户
	 * 
	 * @param 用户信息
	 *            ，逐项信息查询，信息为空说明该查询条件为空
	 * @return 用户信息，若未查到符合条件用户则返回对象为null
	 * @return
	 */
	public User findUser(User one) {
		User user = null;
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			UserDao userDao = new UserDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			user = userDao.findUser(one);
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
//			throw new ServiceException("查询用户错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
		return user;
	}

	/**
	 * 获取用户列表最大行数
	 * 
	 * @return 列表最大行数
	 * @throws SQLException
	 */
	public int getUserListRowCount(User one) {
		Connection conn = null;
		int res = 0;
		try {
			conn = DBUtils.getConnection();
			UserDao userDao = new UserDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = userDao.getUserListRowCount(one);
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
//			throw new ServiceException("查询错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}

		return res;
	}

	/**
	 * 获取用户列表最大页数
	 * 
	 * @param pageSize
	 *            ，每页显示信息条数
	 * @return 列表最大页数
	 * @throws SQLException
	 */
	public int getUserListPageCount(int pageSize, User one) {
		Connection conn = null;
		int res = 0;
		try {
			conn = DBUtils.getConnection();
			UserDao userDao = new UserDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = userDao.getUserListPageCount(pageSize, one);
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
//			throw new ServiceException("查询错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}

		return res;
	}

	/**
	 * 获取指定页用户信息列表，通过分页SQL语句实现
	 * 
	 * @param pageSize
	 *            ，每页显示信息条数
	 * @param pageNo
	 *            ，需要获取的页数
	 * @param one
	 *            ，需要获取的页数
	 * @return 用户信息列表，List[User]，若无满足条件则列表为空
	 * @throws SQLException
	 */
	public ArrayList<User> getUserList(int pageSize, int pageNo, User one) {
		Connection conn = null;
		ArrayList<User> res = null;
		try {
			conn = DBUtils.getConnection();
			UserDao userDao = new UserDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = userDao.getUserList(pageSize, pageNo, one);
			DBUtils.commit(conn);
		} catch (Exception e) {
			DBUtils.rollback(conn);
//			throw new ServiceException("查询错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
		return res;
	}

	/**
	 * 获取指定页用户信息列表，通过分析结果集ResultSet对象实现
	 * 
	 * @param pageSize
	 *            ，每页显示信息条数
	 * @param pageNum
	 *            ，需要获取的页数
	 * @param one
	 *            ，需要获取的页数
	 * @return 用户信息列表，List[User]，若无满足条件则列表为空
	 * @throws SQLException
	 */
	public List<User> getUserListRS(int pageSize, int pageNum, User one) {
		Connection conn = null;
		List<User> res = null;
		try {
			conn = DBUtils.getConnection();
			UserDao userDao = new UserDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = userDao.getUserListRS(pageSize, pageNum, one);
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
//			throw new ServiceException("查询错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}

		return res;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param one
	 *            ，需要更新的用户信息对象
	 * @return 执行是否成功
	 * @throws Exception
	 * @throws SQLException
	 */
	public boolean updateUser(User one) throws Exception {
		Connection conn = null;
		boolean flag = false;
		try {
			conn = DBUtils.getConnection();
			UserDao userDao = new UserDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			if (userDao.updateUser(one) > 0) {
				flag = true;
			}
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
//			throw new ServiceException("更新用户信息错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}

		return flag;
	}
	
	public ArrayList<User> getUserList( int rowcount, User one) {
		Connection conn = null;
		ArrayList<User> res = null;
		try {
			conn = DBUtils.getConnection();
			UserDao userDao = new UserDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = userDao.getUserList(rowcount, one);
			DBUtils.commit(conn);
		} catch (Exception e) {
			DBUtils.rollback(conn);
//			throw new ServiceException("查询错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
		return res;
	}
	
}
