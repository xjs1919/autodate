package com.chrhc.mybatis.autodate.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chrhc.mybatis.autodate.domain.User;

public interface UserMapper {
	
	Integer insertUser(User user);
	
	Integer insertUserAtParam(@Param("user")User user);

	Integer insertUserNoParams(int id, String name,  String password, int version);
	
	Integer insertUserParams(@Param("id")int id, @Param("name")String name,  @Param("password")String password, @Param("version")int version,
			@Param("createUserId")String createUserId, @Param("createBy")String createBy);
	
	Integer insertUserMap(Map<String, Object> user);
		
	Integer insertUserMapAtParam(@Param("user")Map<String, Object> user);
	
	Integer insertUserWithDate(User user);
	
	Integer insertBatchUser(List<User> users);
	
	Integer insertBatchUserAtParam(@Param("users")List<User> users);
	
	Integer insertBatchUserMap(List<Map<String,Object>> users);
	
	Integer insertBatchUserMapAtParam(@Param("users")List<Map<String,Object>> users);
	
	Integer updateVersion(User user);
	
	Integer updateVersionWithDate(User user);
	
	Integer updateBatchVersion(List<User> users);
	
	Integer updateNoBean(@Param("id")int id, @Param("version")int version, @Param("updateUserId")String updateUserId, @Param("updateBy")String updateBy);
	
	Integer resetData();
	
}
