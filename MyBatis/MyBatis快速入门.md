# 开发步骤
- 创建user表，添加数据
- 导入Maven坐标
- 编写MyBatis配置文件，修改链接信息
- 编写SQL语句
- 代码编写
   - 定义POJO类
   - 加载核心配置文件
   - 获取SqlSession对象，执行SQL语句
   - 释放资源
# Maven依赖
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.idea</groupId>
  <artifactId>MyBatisLearning</artifactId>
  <version>1.0-SNAPSHOT</version>
  <name>Archetype - MyBatisLearning</name>
  <url>http://maven.apache.org</url>

  <dependencies>

    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.5</version>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.20</version>
    </dependency>

  </dependencies>
</project>

```
# MyBatis配置信息
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql:///demo?allowPublicKeyRetrieval=true"/>
                <property name="username" value="root"/>
                <property name="password" value="chnn13001400"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="UserMapper.xml"/>
    </mappers>
</configuration>
```
# UserMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="test">

    <select id="selectUserList" resultType="cn.chnn10.pojo.User">
        select * from tb_user
    </select>

</mapper>
```
# User.java
```java
package cn.chnn10.pojo;

// alt + 鼠标左键 整列编辑
public class User {

    private Integer id;
    private String username;
    private String password;
    private String gender;
    private String addr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", gender='" + gender + '\'' +
        ", addr='" + addr + '\'' +
        '}';
    }
}
```
# 数据库表信息
```sql
create database demo;
use demo;

drop table if exists tb_user;

create table tb_user(
	id int primary key auto_increment,
	username varchar(20),
	password varchar(20),
	gender char(1),
	addr varchar(30)
);



INSERT INTO tb_user VALUES (1, 'zhangsan', '123', '男', '北京');
INSERT INTO tb_user VALUES (2, '李四', '234', '女', '天津');
INSERT INTO tb_user VALUES (3, '王五', '11', '男', '西安');


```
# 测试类
```java
package cn.chnn10;

import cn.chnn10.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisDemo {
    public static void main(String[] args) throws IOException {
        // 读取MyBatis的配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 获取这个SqlSession的对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 直接执行SQL
        List<User> userList = sqlSession.selectList("selectUserList");
        System.out.println(userList);

        sqlSession.close();
    }
}


```

![image.png](https://cdn.nlark.com/yuque/0/2024/png/2955721/1713577822051-1ad97906-fef2-4544-836f-ddda44c0faf6.png#averageHue=%23232528&clientId=ubbf1af35-120b-4&from=paste&height=1080&id=u1b39bbed&originHeight=2160&originWidth=3840&originalType=binary&ratio=2&rotation=0&showTitle=false&size=319876&status=done&style=none&taskId=ua2c660c7-4c3f-453a-a58d-617c830a44c&title=&width=1920)


