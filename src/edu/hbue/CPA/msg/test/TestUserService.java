package edu.hbue.CPA.msg.test;

import edu.hbue.CPA.msg.domain.*;
import edu.hbue.CPA.msg.service.UserService;

public class TestUserService {
	UserService service = UserService.getInstance();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestUserService t = new TestUserService();
		
		try {
			UserService service = UserService.getInstance();
			User luser;
			luser = service.login("admin", "admin");
			if(luser==null)
			System.out.print(luser);
			else
				System.out.println(luser);
				System.out.println(luser.getId()+":"+luser.getUsername()+"."+luser.getPassword()+"."+luser.getRule()+".");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
//		t.testAddUser();
		t.testFind();
//		try {
//			t.testUpdateUser();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		t.testDeleteUsers();
//		try {
//			t.testLogin();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		t.testGetUserList();
//		t.TestGetUserListRowCount();
		
	}

	// 测试添加用户
	// @Test
	public void testAddUser() {
		// fail("Not yet implemented");
		// UserService service = UserService.getInstance();
		User user = new User();
		user.setUsername("testuser");
		System.out.println("添加用户"+service.addUser(user));
	}

	// 测试修改加用户
	// @Test
	public void testUpdateUser() throws Exception {
		User user = new User();
		// 将用户id为25(测试用户一定要在数据表中)的sex修改为"0"
		// 一定要设置用户的ＩＤ,不然操作不成功
		// 因为：：如果对象没有封装ＩＤ，ＳＱＬ语句就不知道要操作哪条记录，
		// 以致会对整个表相应字段修改成同样的内容,这个是不允许的。

		user.setId(5);
		User fu = new User();
		fu = service.findUser(user);
		fu.setUsername("usertt");
		fu.setId(user.getId());
		// fu.setUsername("111");
		System.out.println("修改用户："+service.updateUser(fu));
	}

	// 测试删除用户
	// @Test
	public void testDeleteUsers() {
		// fail("Not yet implemented");
		// UserService service = UserService.getInstance();
		// 保证数据表中有需要删除的真实数据
		int[] userIdList = { 26 };
		System.out.println("删除用户："+service.deleteUsers(userIdList));
	}

	// 　测试登录
	// @Test
	public void testLogin() throws Exception {
		// fail("Not yet implemented");
		// UserService service = UserService.getInstance();
		User user = service.login("admin", "admin");
		// Assert.assertNotNull(user);
		System.out.println(user);
	}

	// 测试获取用户列表最大行数
	// @Test
	public void TestGetUserListRowCount() {
		User u = new User();// 什么都不填充，意味着所有用户
//		u.setUsername("test");
		System.out.println("表中总共有记录数：" + service.getUserListRowCount(u));
	}


	// 测试查找用户
	// @Test
	public void testFind() {

		// 生成id=5的对象，持这个对象去查询，应该能找出编号为5的记录
		// userdaoimpl中的查询代码
		// 查询条件id字段
		// Integer id = one.getId();
		// if (id != null && id != 0) {
		// find_sql.append(" AND u.id=" + id);
		// tag = true;
		// }
		// select .. from .. where ID=5;
		User findUser = new User();
		findUser.setUsername("admin");
		User user = service.findUser(findUser);
		System.out.println(user);
		// Assert.assertNotNull(user);
		// Assert.assertTrue(user.getRealname().equals("陈老师2"));
	}

	// 测试得到用户列表，若无用户限制，则为findALl
	//@Test
	public void testGetUserList() {
		// Assert.assertNotNull(service.getUserList(0, 0, new User()));
		// Assert.assertEquals(3, service.getUserList(10, 1, new
		// User()).size());
		User findUser = new User();
		findUser.setUsername("test");
		System.out.println("数据共有条数:" + service.getUserListRowCount(findUser));
		System.out.println("数据共有页数:"
				+ service.getUserListPageCount(5, findUser));
		for (User u : service.getUserList(5, 1, findUser)) {
			System.out.println(u.getId() + "  " + u.getUsername());
		}
	}
}
