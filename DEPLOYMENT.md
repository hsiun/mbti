# MBTI项目部署文档

## 一、部署环境

| 组件 | 版本 | 说明 |
|------|------|------|
| 操作系统 | CentOS 10 (RHEL 10) | Linux 6.12.0-192.el10.x86_64 |
| JDK | 11.0.22 (Temurin) | /opt/jdk-11.0.22+7 |
| Maven | 3.9.6 | /opt/apache-maven-3.9.6 |
| 数据库 | MariaDB 10.11.15 | MySQL兼容 |
| 端口 | 8888 | 应用端口 |
| Context Path | /mbti | 访问路径 |

---

## 二、环境变量配置

```bash
# 添加到 /etc/profile
export JAVA_HOME=/opt/jdk-11.0.22+7
export MAVEN_HOME=/opt/apache-maven-3.9.6
export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
```

---

## 三、数据库配置

### 3.1 创建数据库和用户

```sql
CREATE DATABASE IF NOT EXISTS mbti DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE USER IF NOT EXISTS 'mbti'@'localhost' IDENTIFIED BY 'Mbti@2026#Pass';
GRANT ALL PRIVILEGES ON mbti.* TO 'mbti'@'localhost';
FLUSH PRIVILEGES;
```

### 3.2 导入数据

```bash
mysql mbti < mbti-admin/src/mbti.sql
```

---

## 四、遇到的错误及解决方案

### 错误1: sun.misc.BASE64Encoder 找不到

**问题描述：**
```
[ERROR] cannot find symbol
  symbol:   class BASE64Encoder
  location: package sun.misc
```

**原因：** 
JDK 9+ 移除了 `sun.misc.BASE64Encoder` 类，这是JDK内部API。

**解决方案：**
修改 `WordImageConvertor.java`，使用标准的 `java.util.Base64` 替代：

```java
// 旧代码
import sun.misc.BASE64Encoder;
BASE64Encoder encoder = new BASE64Encoder();
pictureBuffer.append(encoder.encode(out.toByteArray()));

// 新代码
import java.util.Base64;
Base64.Encoder encoder = Base64.getMimeEncoder();
pictureBuffer.append(encoder.encodeToString(out.toByteArray()));
```

---

### 错误2: Gson包不存在

**问题描述：**
```
[ERROR] package com.google.gson does not exist
```

**原因：** 
项目使用了Gson但pom.xml中没有声明依赖。

**解决方案：**
在 `pom.xml` 中添加Gson依赖：

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

---

### 错误3: RespResult.success() 方法不存在

**问题描述：**
```
[ERROR] cannot find symbol
  symbol:   method success(...)
  location: class com.mbti.common.utils.RespResult
```

**原因：** 
`RespResult` 类没有静态 `success()` 方法，需要使用构造函数。

**解决方案：**
修改 `QuizController.java`：

```java
// 旧代码
return RespResult.success(list);

// 新代码
return new RespResult(RespResult.SUCCESS, list);
```

---

### 错误4: MySQL驱动版本兼容

**问题描述：**
MySQL 5.1.x 驱动与新版本MySQL/MariaDB存在兼容性问题。

**解决方案：**
升级MySQL驱动版本并修改驱动类名：

```xml
<!-- pom.xml -->
<mysql.version>8.0.33</mysql.version>
```

```yaml
# application-prod.yml
driver-class-name: com.mysql.cj.jdbc.Driver
url: jdbc:mysql://localhost:3306/mbti?...&serverTimezone=Asia/Shanghai
```

---

### 错误5: 端口被占用

**问题描述：**
```
The Tomcat connector configured to listen on port 8888 failed to start. 
The port may already be in use.
```

**解决方案：**
```bash
# 查找并杀掉占用端口的进程
netstat -tlnp | grep 8888
kill -9 <PID>
```

---

## 五、启动服务

### 5.1 构建项目

```bash
cd /path/to/mbti
mvn clean package -DskipTests
```

### 5.2 启动应用

```bash
cd mbti-admin/target
java -jar mbti.admin.jar --spring.profiles.active=prod
```

### 5.3 后台运行

```bash
nohup java -jar mbti.admin.jar --spring.profiles.active=prod > /tmp/mbti.log 2>&1 &
```

---

## 六、访问信息

| 项目 | 值 |
|------|-----|
| **访问地址** | http://服务器IP:8888/mbti |
| **管理员账号** | admin |
| **管理员密码** | admin |
| **API文档** | http://服务器IP:8888/mbti/swagger-ui.html |

---

## 七、防火墙配置

```bash
# 开放8888端口
firewall-cmd --permanent --add-port=8888/tcp
firewall-cmd --reload
```

---

## 八、验证部署

```bash
# 测试API
curl http://localhost:8888/mbti/api/question/list/1

# 检查端口
netstat -tlnp | grep 8888

# 查看日志
tail -f /tmp/mbti.log
```

---

## 九、文件变更清单

| 文件 | 变更类型 | 说明 |
|------|----------|------|
| `.gitignore` | 新增 | 忽略编译产物 |
| `WordImageConvertor.java` | 修改 | JDK11兼容性修复 |
| `QuizController.java` | 修改 | 修复RespResult调用 |
| `pom.xml` | 修改 | 添加gson依赖，升级mysql驱动 |
| `application-prod.yml` | 修改 | 更新数据库配置 |

---

## 十、部署时间

- 开始时间: 2026-03-01 05:04 UTC
- 完成时间: 2026-03-01 12:35 UTC
- 总耗时: 约7.5小时（包含依赖下载和问题排查）

---

*文档生成时间: 2026-03-01*
