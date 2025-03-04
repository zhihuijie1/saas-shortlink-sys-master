# saas-shortlink-sys-master
短链接saas系统



## git使用说明

github：saas-shortlink-sys-master

本地：shortlink



本地环境下：

```shell
git init

git add . 

git commit -m "1"

git remote add origin git@github.com:zhihuijie1/saas-shortlink-sys-master.git

git branch
// 显示本地是 * master分支，但是github是main分支，需要修改一下分支

git branch -M main

//远程仓库中的 main 分支包含你本地没有的提交。为了解决这个问题，你需要先将远程 main 分支的更改拉取到本地，然后再推送。
git pull origin main --rebase

git push --set-upstream origin main

```

## MyBatis-Plus  - `baseMapper`常用方法：

在 MyBatis-Plus 中，`baseMapper` 是一个通用的 Mapper 接口，包含了许多常用的数据库操作方法。这里列出一些常用的 `baseMapper` 方法，帮助你在日常开发中高效使用它们。

**1. `selectOne(QueryWrapper<T> queryWrapper)`**

- **作用**：根据条件查询单条记录。

- 示例

  ```
  LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class)
                                                    .eq(UserDo::getUsername, username);
  UserDo userDo = baseMapper.selectOne(queryWrapper);
  ```

**2. `selectById(Serializable id)`**

- **作用**：根据主键 ID 查询一条记录。

- 示例

  ```
  UserDo user = baseMapper.selectById(1);
  ```

**3. `selectList(QueryWrapper<T> queryWrapper)`**

- **作用**：根据条件查询多条记录，返回 `List` 结果。

- 示例

  ```
  LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class)
                                                    .gt(UserDo::getAge, 18);
  List<UserDo> users = baseMapper.selectList(queryWrapper);
  ```

4. **`selectBatchIds(Collection<? extends Serializable> idList)`**

- **作用**：根据多个主键 ID 查询多条记录。

- 示例

  ```
  List<Long> ids = Arrays.asList(1L, 2L, 3L);
  List<UserDo> users = baseMapper.selectBatchIds(ids);
  ```

5. **`selectCount(QueryWrapper<T> queryWrapper)`**

- **作用**：根据条件查询记录数量，返回符合条件的记录数。

- 示例

  ```
  java复制代码LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class)
                                                    .gt(UserDo::getAge, 18);
  int count = baseMapper.selectCount(queryWrapper);
  ```

6. **`selectPage(Page<T> page, QueryWrapper<T> queryWrapper)`**

- **作用**：根据条件查询分页结果。

- 示例

  ```
  Page<UserDo> page = new Page<>(1, 10); // 第1页，每页10条
  LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class)
                                                    .gt(UserDo::getAge, 18);
  Page<UserDo> result = baseMapper.selectPage(page, queryWrapper);
  List<UserDo> users = result.getRecords();
  ```

7. **`**insert(T entity)`****

- **作用**：插入一条新记录。

- 示例

  ```
  UserDo newUser = new UserDo();
  newUser.setUsername("newUser");
  newUser.setAge(25);
  int rows = baseMapper.insert(newUser); // 返回影响的行数
  ```

8. **`updateById(T entity)`**

- **作用**：根据主键 ID 更新记录。

- 示例

  ```
  UserDo user = new UserDo();
  user.setId(1L);
  user.setAge(30);
  int rows = baseMapper.updateById(user); // 返回影响的行数
  ```

9. **`update(T entity, QueryWrapper<T> updateWrapper)`**

- **作用**：根据条件更新记录。

- 示例

  ```
  UserDo user = new UserDo();
  user.setAge(35);
  
  LambdaUpdateWrapper<UserDo> updateWrapper = Wrappers.lambdaUpdate(UserDo.class)
                                                     .eq(UserDo::getUsername, "oldUsername");
  
  int rows = baseMapper.update(user, updateWrapper); // 返回影响的行数
  ```

10. **`deleteById(Serializable id)`**

- **作用**：根据主键 ID 删除记录。

- 示例

  ```
  int rows = baseMapper.deleteById(1L); // 返回影响的行数
  ```

11. **`deleteBatchIds(Collection<? extends Serializable> idList)`**

- **作用**：根据多个主键 ID 删除多条记录。

- 示例

  ```
  List<Long> ids = Arrays.asList(1L, 2L, 3L);
  int rows = baseMapper.deleteBatchIds(ids);
  ```

12. **`**delete(QueryWrapper<T> queryWrapper)`****

- **作用**：根据条件删除记录。

- 示例

  ```java
  LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class)
                                                    .eq(UserDo::getUsername, "userToDelete");
  int rows = baseMapper.delete(queryWrapper);
  ```

这些方法是 MyBatis-Plus 的 `baseMapper` 中的常用方法，可以帮助你快速进行 CRUD（增删查改）操作，大大简化了常规的数据库操作逻辑。







## 用户模块功能

- 检查用户名是否存在
- 注册用户
- 修改用户
- 根据用户名查询用户
- 用户登录
- 检查用户是否登录
- 用户退出登录
- 注销用户





## 常见的异常：

### 1：jackson序列化异常

com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class 

com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class com.saas.admin.dto.resp.UserRespDTO

这个错误是一个 **Jackson 序列化问题**，具体原因是



```
原因分析： 在 Spring Boot 中，Jackson 用于将对象序列化为 JSON 格式，但在这个错误中，Jackson 无法找到 UserRespDTO 类的序列化器，可能有以下原因：

UserRespDTO 缺少 Getter/Setter 方法：Jackson 需要通过 Getter/Setter 方法来序列化对象的属性。如果 UserRespDTO 没有公开的 Getter 方法或标注了 @JsonIgnore，那么 Jackson 无法获取属性值。

UserRespDTO 没有任何可序列化的字段：如果该类没有任何公共字段、没有 Getter 方法或没有被 @JsonProperty 标注的字段，Jackson 可能会认为此类为空，从而抛出此错误。

UserRespDTO 没有被标记为 Serializable：虽然在 Spring Boot 中这不是强制性的，但添加 Serializable 接口有时能避免序列化问题。
```







## 加强点



### 1：登录验证功能

判断当前的用户是否登录，可以使用JWT进行校验 - 深入理解 JWT，和 spring security，也可以使用redis进行存储。







## maven：

### 1：父模块的打包形式  - pom

父模块以pom的形式打包，所以父模块的插件是不会打到jar包里面的，只有具体的子模块打包的时候才会打到jar包里面

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.saas</groupId>
    <artifactId>shortlink</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>shortlink</name>
    <description>shortlink</description>
    <packaging>pom</packaging>

    <modules>
        <module>admin</module>
    </modules>

    <!--版本信息-->

    <properties>
        <java.version>17</java.version>
        <spring-boot.version>3.0.7</spring-boot.version>
        <spring-cloud.version>2022.0.3</spring-cloud.version>
        <spring-cloud-alibaba.version>2022.0.0.0-RC2</spring-cloud-alibaba.version>
        <mybatis-spring-boot-starter.version>3.0.2</mybatis-spring-boot-starter.version>
        <shardingsphere.version>5.3.2</shardingsphere.version>
        <jjwt.version>0.9.1</jjwt.version>
        <fastjson2.version>2.0.36</fastjson2.version>
        <mybatis-plus.version>3.5.3.1</mybatis-plus.version>
        <dozer-core.version>6.5.2</dozer-core.version>
        <hutool-all.version>5.8.20</hutool-all.version>
        <redisson.version>3.21.3</redisson.version>
        <guava.version>30.0-jre</guava.version>
    </properties>
    <!--dependencies 统一依赖管理，子模块不用自己再加了-->
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.30</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <!--依赖管理 后面的子模块还需要自己加-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-boot-starter</artifactId>
                <version>${mybatis-plus.version}</version>
            </dependency>

            <dependency>
                <groupId>org.apache.shardingsphere</groupId>
                <artifactId>shardingsphere-jdbc-core</artifactId>
                <version>${shardingsphere.version}</version>
            </dependency>

            <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt</artifactId>
                <version>${jjwt.version}</version>
            </dependency>

            <dependency>
                <groupId>com.alibaba.fastjson2</groupId>
                <artifactId>fastjson2</artifactId>
                <version>${fastjson2.version}</version>
            </dependency>

            <dependency>
                <groupId>com.github.dozermapper</groupId>
                <artifactId>dozer-core</artifactId>
                <version>${dozer-core.version}</version>
            </dependency>

            <dependency>
                <groupId>cn.hutool</groupId>
                <artifactId>hutool-all</artifactId>
                <version>${hutool-all.version}</version>
            </dependency>

            <dependency>
                <groupId>org.redisson</groupId>
                <artifactId>redisson-spring-boot-starter</artifactId>
                <version>${redisson.version}</version>
            </dependency>

            <dependency>
                <groupId>com.google.guava</groupId>
                <artifactId>guava</artifactId>
                <version>${guava.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.6.1</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>

        </plugins>
    </build>
</project>

```



## 阿里云服务器：

### 如何远程连接阿里云服务器中的mysql

购买阿里云服务器以后进入如下界面

![image-20250205012327947](D:/%E4%BD%A0%E5%A5%BDJava/image-20250205012327947.png)

点击安全组，点击管理规则，来添加我们端口，允许外部访问，这里添加了8888端口，允许宝塔linux来配置我们的服务器

![image-20250205012632733](D:/%E4%BD%A0%E5%A5%BDJava/image-20250205012632733.png)

在宝塔linux面板中我可以提前安装一些组件（mysql / jdk 。。。。）来提前配置一下我的服务器。

在宝塔linux面板中点击mysql，点击root密码，来修改我们的数据库密码，点击添加数据库来创建我们的数据库。

![image-20250205012845096](D:/%E4%BD%A0%E5%A5%BDJava/image-20250205012845096.png)

![image-20250205013128088](D:/%E4%BD%A0%E5%A5%BDJava/image-20250205013128088.png)

点击安全，添加上我们的数据库端口，允许外部访问

![image-20250205013017077](D:/%E4%BD%A0%E5%A5%BDJava/image-20250205013017077.png)

此时在navicat中输入即可连接远程服务器

![image-20250205013741096](D:/%E4%BD%A0%E5%A5%BDJava/image-20250205013741096.png)























