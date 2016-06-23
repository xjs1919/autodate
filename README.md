# MyBatis自动插入日期插件 #

### MyBatis Auto Date Plugin ###

[![Build Status](https://travis-ci.org/mybatis/mybatis-3.svg?branch=master)](https://travis-ci.org/mybatis/mybatis-3)
[![Coverage Status](https://coveralls.io/repos/mybatis/mybatis-3/badge.svg?branch=master&service=github)](https://coveralls.io/github/mybatis/mybatis-3?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/56199c04a193340f320005d3/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56199c04a193340f320005d3)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Stack Overflow](http://img.shields.io/:stack%20overflow-mybatis-brightgreen.svg)](http://stackoverflow.com/questions/tagged/mybatis)
[![Project Stats](https://www.openhub.net/p/mybatis/widgets/project_thin_badge.gif)](https://www.openhub.net/p/mybatis)

![mybatis](http://mybatis.github.io/images/mybatis-logo.png)

----------

## Document: ##
	
----------

	描述：本插件主要是为了给Insert和Update语句自动添加上当前时间，方便开发人员开发。

----------
### 1. 使用方式：在mybatis配置文件中加入如下配置，就完成了。 ###
	<plugins>
		<plugin interceptor="com.chrhc.mybatis.autodate.interceptor.AutoDateInterceptor">
			<property name="createDateColumn" value="create_date"/>
			<property name="updateDateColumn" value="update_date"/>
		</plugin>
	</plugins>
----------

### 2. 对插件配置的说明： ###
	
	create_date和update_date代表数据库表中的创建时间和更新时间，这两个可以只配置一个，也可以两个都配置。
	如果两个都不配置，则相当于插件不起作用。


----------

### 3. 效果： ###
> 之前：**insert into smart_user(id, name, password, version)values(?, ?, ?, ?)**

> 之后：**INSERT INTO smart_user (id, name, password, version, create_date) VALUES (?, ?, ?, ?, {ts '2016-06-23 15:01:05.55'})**

> 之前：**update smart_user set version = ? where id = ?**

> 之后：**UPDATE smart_user SET version = ?, update_date = {ts '2016-06-23 15:33:03.644'} WHERE id = ?**

----------


### 4.关于插件： ###
	如果您有什么建议或者意见，欢迎留言，也欢迎pull request，作者会将你优秀的思想加入到插件里面来，为其他人更好的解决问题。

----------

### 5.关于作者： ###
	作者QQ：605162215
	作者邮箱：605162215@qq.com
