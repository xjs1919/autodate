package com.chrhc.mybatis.autodate.mapper.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import com.chrhc.mybatis.autodate.domain.User;
import com.chrhc.mybatis.autodate.mapper.UserMapper;

public class UserMapperTest {
	
	private static SqlSession sqlSession = null;
	
	@BeforeClass
	public static void doInitTest() throws Exception {
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		sqlSession = sqlSessionFactory.openSession(true);
	}
	
	//@After
	public void resetDatabaseTest() {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.resetData();
	}
	
	@Test
	public void testInsert() throws Exception {
		User user = new User();
		user.setId(100);
		user.setName("test");
		user.setPassword("test");
		user.setCreateUserId("create_user_id_100");
		user.setCreateBy("create_by_100");
		user.setVersion(100L);
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.insertUser(user);
	}
	
	@Test
	public void testInsertAtParam() throws Exception {
		User user = new User();
		user.setId(100);
		user.setName("test");
		user.setPassword("test");
		user.setCreateUserId("xjs");
		user.setCreateBy("create_by_100");
		user.setVersion(100L);
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.insertUserAtParam(user);
	}
	
//	@Test
//	public void testInsertUserNoParams() throws Exception {
//		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//		userMapper.insertUserNoParams(100, "name", "pass", 111);
//	}
	
	@Test
	public void testInsertParams() throws Exception {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.insertUserParams(100, "name", "pass", 111, "lll","create_by_100");
	}
	
	
	@Test
	public void testInsertUserMap() throws Exception {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("id", "100");
		user.put("name", "name100");
		user.put("password", "pass100");
		user.put("createUserId", "user100");
		user.put("createBy", "create_by_100");
		userMapper.insertUserMap(user);
	}
	
	@Test
	public void testInsertUserMapAtParam() throws Exception {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("id", "100");
		user.put("name", "name100");
		user.put("password", "pass100");
		user.put("createUserId", "user100");
		user.put("createBy", "create_by_100");
		userMapper.insertUserMapAtParam(user);
	}
	
	@Test
	public void testInsertBatch() throws Exception {
		List<User> users = new ArrayList<User>();
		
		User user = new User();
		user.setId(200);
		user.setName("test");
		user.setPassword("test");
		user.setVersion(1L);
		user.setCreateUserId("user1");
		user.setCreateBy("createBy200");
		users.add(user);
		
		user = new User();
		user.setId(201);
		user.setName("test");
		user.setPassword("test");
		user.setVersion(2L);
		user.setCreateUserId("user2");
		user.setCreateBy("createBy201");
		users.add(user);
		
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.insertBatchUser(users);
	}
	
	@Test
	public void testInsertBatchAtParam() throws Exception {
		List<User> users = new ArrayList<User>();
		
		User user = new User();
		user.setId(200);
		user.setName("test");
		user.setPassword("test");
		user.setVersion(1L);
		user.setCreateUserId("user11");
		user.setCreateBy("createBy200");
		users.add(user);
		
		user = new User();
		user.setId(201);
		user.setName("test");
		user.setPassword("test");
		user.setVersion(2L);
		user.setCreateUserId("user22");
		user.setCreateBy("createBy201");
		users.add(user);
		
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.insertBatchUserAtParam(users);
	}
	
	@Test
	public void testInsertBatchUserMap() throws Exception {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("id", "200");
		user.put("name", "name200");
		user.put("password", "pass200");
		user.put("createUserId", "user200");
		user.put("createBy", "create_by_200");
		users.add(user);
		
		user = new HashMap<String, Object>();
		user.put("id", "201");
		user.put("name", "name201");
		user.put("password", "pass201");
		user.put("createUserId", "user201");
		user.put("createBy", "create_by_201");
		users.add(user);
		
		userMapper.insertBatchUserMap(users);
	}
	
	@Test
	public void testInsertBatchUserMapAtParam() throws Exception {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("id", "200");
		user.put("name", "name200");
		user.put("password", "pass200");
		user.put("createUserId", "user200");
		user.put("createBy", "create_by_200");
		users.add(user);
		
		user = new HashMap<String, Object>();
		user.put("id", "201");
		user.put("name", "name201");
		user.put("password", "pass201");
		user.put("createUserId", "user201");
		user.put("createBy", "create_by_201");
		users.add(user);
		
		userMapper.insertBatchUserMapAtParam(users);
	}
	
	
	@Test
	public void testUpdate() throws Exception {
		User user = new User();
		user.setId(101);
		user.setVersion(101L);
		user.setUpdateUserId("updateUser100");
		user.setUpdateBy("update_by_100");
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.updateVersion(user);
	}

	@Test
	public void testUpdateBatch() throws Exception {
		List<User> users = new ArrayList<User>();
		
		User user = new User();
		user.setId(200);
		user.setVersion(200L);
		user.setUpdateUserId("updateUser200");
		user.setUpdateBy("update_by_200");
		users.add(user);
		
		user = new User();
		user.setId(201);
		user.setVersion(201L);
		user.setUpdateUserId("updateUser201");
		user.setUpdateBy("update_by_201");
		users.add(user);
		
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.updateBatchVersion(users);
	}
	
	@Test
	public void testUpdateNoBean() throws Exception {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.updateNoBean(100, 0, "userid", "user");
	}
	
}
