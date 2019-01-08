# Mybatis-Plus 分享

![mybatis-plus图像](https://mp.baomidou.com/img/logo.png)

[TOC]

## 一、简单介绍

### 官方说明 ：

Mybatis-Plus（简称MP）是一个Mybatis的增强工具，在 Mybatis 的基础上只做增强不做改变，为简化开发而生！

- #### 润物无声

  只做增强不做改变，引入它不会对现有工程产生影响。

- #### 效率至上

  只需简单配置，即可快速进行 CRUD 操作，从而节省大量时间。

- #### 丰富功能

  热加载、代码生成、分页、性能分析等功能一应俱全。

### 成绩：

MyBatis-Plus 荣获[【2018年度开源中国最受欢迎的中国软件】](https://www.oschina.net/question/2896879_2290300) TOP5 	

![开元中国排名](https://oscimg.oschina.net/oscnet/2bf976658b2c353bbe308306d64d1b129aa.jpg)	

### 最新版本：

``` xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus</artifactId>
    <version>3.0.7.1</version>
</dependency>
```

### 开发层面MyBatis-Plus特色

1. **代码生成**
2. **条件构造器**

### Mybatis-Plus中的Plus

![like](https://mp.baomidou.com/img/relationship-with-mybatis.png)

## 二、MP的特性

- **无侵入**：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
- **损耗小**：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
- **强大的 CRUD 操作**：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
- **支持 Lambda 形式调用**：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
- **内置代码生成器**：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 **Service 、 Controller** 层代码，支持模板引擎，更有超多自定义配置等您来使用
- **内置分页插件**：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询
- **内置全局拦截插件**：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作
- **支持多种数据库**：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer2005、SQLServer 等多种数据库
- **支持主键自动生成**：支持多达 4 种主键策略（`内含分布式唯一 ID 生成器 - Sequence`），可自由配置，完美解决主键问题
- **支持 XML 热加载**：<u>Mapper 对应的 XML 支持热加载，对于简单的 CRUD 操作</u>，甚至可以无 XML 启动

- **支持 ActiveRecord 模式**：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
- **支持自定义全局通用操作**：支持全局通用方法注入（ Write once, use anywhere ）
- **支持关键词自动转义**：支持数据库关键词（order、key......）自动转义，还可自定义关键词
- **内置性能分析插件**：可输出 Sql 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
- **内置 Sql 注入剥离器**：支持 Sql 注入剥离，有效预防 Sql 注入攻击

## 三、MP框架结构

![框架](https://mp.baomidou.com/img/mybatis-plus-framework.jpg)





## 四、简单的入门Demo(Mysql)

#### github 地址：

https://github.com/wunian7yulian/Mybatis-Plus/tree/master/simpledemo

#### demo 环境： 

​	windows 7

​	jdk 1.8.0.40

​	idea Ultimate 

​	maven 3.3.9	

#### 初始化：

##### 数据库：MySql

###### DDL:

```sql
-- 创建简单表格
DROP TABLE IF EXISTS user;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```

###### DML:

```sql
-- 初始化数据
DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
```

#### 工程：

​	为了方便快捷 选用 SpringBoot 工程作为demo

##### 第一步、创建工程

![1546839886488](/assets/1546839886488.png)

输入项目包名创建完毕。

##### 第二步、引入依赖坐标

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.0.7.1</version>
</dependency>
```

##### 第三步、配置数据源

在 `application.yml` 配置文件中添加相关配置：

```yaml
# DataSource Config
spring:
  datasource:
    # 这里如果有错误是因为 maven mysql包 选择了 runtime 形式的 scope  可以不用管它 继续下一步就好
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/mp_demo_db?characterEncoding=utf8
    username: root
    password: 123456
```

##### 第四步、添加mybatis扫描位置

```java

@SpringBootApplication
@MapperScan("com.lynwood.mp.simpledemo.mapper")
public class SimpledemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpledemoApplication.class, args);
    }

}
```

##### 第五步、pojo及mapper

pojo:

```java
import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

mapper:

```java
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lynwood.mp.simpledemo.model.User;

public interface UserMapper extends BaseMapper<User> {

}
```

##### 第六步、测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}
```

运行结果：

```java
User{id=1, name='Jone', age=18, email='test1@baomidou.com'}
User{id=2, name='Jack', age=20, email='test2@baomidou.com'}
User{id=3, name='Tom', age=28, email='test3@baomidou.com'}
User{id=4, name='Sandy', age=21,email='test4@baomidou.com'}
User{id=5, name='Bill', age=24,email='test5@baomidou.com'}
```

## 五、核心功能

### 简便之-代码生成器（AutoGenerator）

​	AutoGenerator 是 MyBatis-Plus 的代码生成器，通过 AutoGenerator 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码，极大的提升了开发效率。











## 参考文档：

> MyBatis-Plus 官网： https://mp.baomidou.com/
>
> MyBatis-Plus 配置进阶： https://mp.baomidou.com/config/
>
> MyBatis-Plus 代码生成器配置https://mp.baomidou.com/config/generator-config.html
>
> CnBlogs 为什么用ORM：https://www.cnblogs.com/bobositlife/articles/what-is-orm-why-use-orm.html
>
> oKong简书 Mybatis-Plus使用全解：https://www.jianshu.com/p/7299cba2adec
>
>

