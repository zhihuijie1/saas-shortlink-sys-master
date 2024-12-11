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



