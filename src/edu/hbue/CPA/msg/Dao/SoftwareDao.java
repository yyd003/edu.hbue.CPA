package edu.hbue.CPA.msg.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.hbue.CPA.msg.domain.Software;
import edu.hbue.CPA.msg.domain.User;

public interface SoftwareDao {
	/*	
	 * 增加软件
	 */
	int save(Software Software) throws Exception;
	/*
	 * 删除软件
	 */
	int deleteSoftware(int[] userIdList) throws SQLException;
	/*
	 * 获取软件列表最大行数
	 */
	int getSoftwareListRowCount(Software one) throws SQLException;
	/*
	 * 更新信息
	 */
	int updateSoftware(Software one) throws Exception;
	ArrayList<Software> getSoftwareList(int rowNum, Software one) throws Exception;
	public Software findSoftware(Software one) throws SQLException;
}
