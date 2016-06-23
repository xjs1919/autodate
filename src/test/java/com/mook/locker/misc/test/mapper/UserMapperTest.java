package com.mook.locker.misc.test.mapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mook.locker.misc.domain.User;
import com.mook.locker.misc.mapper.UserMapper;

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
		user.setVersion(100L);
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.insertUser(user);
	}
	
	@Test
	public void testInsertParams() throws Exception {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.insertUserParams(100, "name", "pass", 111);
	}
	
	@Test
	public void testInsertWithDate() throws Exception {
		User user = new User();
		user.setId(100);
		user.setName("test");
		user.setPassword("test");
		user.setVersion(100L);
		user.setCreateDate1(new Date());
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.insertUserWithDate(user);
	}
	
	
	@Test
	public void testInsertBatch() throws Exception {
		List<User> users = new ArrayList<User>();
		
		User user = new User();
		user.setId(200);
		user.setName("test");
		user.setPassword("test");
		user.setVersion(100L);
		users.add(user);
		
		user = new User();
		user.setId(201);
		user.setName("test");
		user.setPassword("test");
		user.setVersion(100L);
		users.add(user);
		
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.insertBatchUser(users);
	}
	
	@Test
	public void testUpdate() throws Exception {
		User user = new User();
		user.setId(100);
		user.setVersion(101L);
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.updateVersion(user);
	}
	
	@Test
	public void testUpdateWithDate() throws Exception {
		User user = new User();
		user.setId(100);
		user.setVersion(101L);
		user.setUpdateDate1(new Date());
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.updateVersionWithDate(user);
	}

	@Test
	public void testUpdateBatch() throws Exception {
		List<User> users = new ArrayList<User>();
		
		User user = new User();
		user.setId(200);
		user.setVersion(200L);
		users.add(user);
		
		user = new User();
		user.setId(201);
		user.setVersion(201L);
		users.add(user);
		
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.updateBatchVersion(users);
	}
	
}
