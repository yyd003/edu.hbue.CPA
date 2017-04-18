package edu.hbue.CPA.msg.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.hbue.CPA.common.DBUtils;
import edu.hbue.CPA.msg.Dao.*;
import edu.hbue.CPA.msg.domain.*;

public class SoftwareService {
	private static final SoftwareService instance = new SoftwareService();

	public static SoftwareService getInstance() {
		return instance;
	}
	
	/**
	 * 增加用户
	 * 
	 * @param user
	 *            用户对象
	 */
	public boolean addSoftware(Software Software) {
		boolean f = false;// 返回添加成功与否的标志，初始不成功
		Connection conn = null;
		try {
			conn = edu.hbue.CPA.common.DBUtils.getConnection();
			SoftwareDao SoftwareDao = new SoftwareDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			if (SoftwareDao.save(Software) > 0) {
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
	
	public boolean deleteSoftware(int[] userIdList) {
		boolean f = false;
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			SoftwareDao userDao = new SoftwareDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			if (userDao.deleteSoftware(userIdList) > 0) {
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
	public boolean updateSoftware(Software one) throws Exception {
		Connection conn = null;
		boolean flag = false;
		try {
			conn = DBUtils.getConnection();
			SoftwareDao userDao = new SoftwareDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			if (userDao.updateSoftware(one) > 0) {
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

	public Software findSoftware(Software one) {
		// TODO Auto-generated method stub
		Software user = null;
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			SoftwareDao SoftwareDao = new SoftwareDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			user = SoftwareDao.findSoftware(one);
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
//			throw new ServiceException("查询用户错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
//		System.out.println(user);
		return user;
	}
	public int getSoftwareListRowCount(Software one) {
		Connection conn = null;
		int res = 0;
		try {
			conn = DBUtils.getConnection();
			SoftwareDao userDao = new SoftwareDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = userDao.getSoftwareListRowCount(one);
			DBUtils.commit(conn);
		} catch (SQLException e) {
			DBUtils.rollback(conn);
//			throw new ServiceException("查询错误", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
//		System.out.println(res);
		return res;
	}
	public ArrayList<Software> getSoftwareList( int rowcount, Software one) {
		Connection conn = null;
		ArrayList<Software> res = null;
		try {
			conn = DBUtils.getConnection();
			SoftwareDao userDao = new SoftwareDaoImpl(conn);
			DBUtils.beginTransaction(conn);
			res = userDao.getSoftwareList(rowcount, one);
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
