# springboot-redis
开始一个springboot项目,包含redis session.

## 项目目录结构
```
  thone
  |---- advice                            # 全局异常处理
  |---- annotation                        # 自定义注解目录
  |---- aspect                            # 切面处理，包含日志切面
  |---- config                            # 配置文件，包括url、资源拦截配置、redis配置等
  |---- entity                            # 实体类
  |---- factory                           # 工厂类
  |---- function                          # 项目业务包
  |  |---- user                           # 用户模块，包含controller与service
  |  |  |---- api                         # 请求与返回的实体类
  |---- pojo                              # pojo类
  |---- repository                        # 数据库操作接口
  |---- util                              # 工具类
```

## 环境准备
```
  |---- java 1.8
  |---- mysql 5.7+
  |---- redis 5.0+  
```

## 配置修改
打开application.yml,修改密码配置
```yaml
spring:
  redis:
    host: 127.0.0.1 # redis地址
    password: ****  # redis密码
  datasource:
    name: ****      # mysql数据库名，请先创建数据库
    username: root  # mysql用户名
    password: ****  # mysql密码
    url: jdbc:mysql://localhost:3306/****?useSSL=false&serverTimezone=GMT%2b8 # 此处的****修改为数据库名
```

## 运行项目
```bash
mvn clean package
cd target
nohup java -jar springboot-redis-0.0.1.jar > /dev/null  2>&1 & #运行程序
curl http://localhost:8080/test/hello # 访问测试接口
kill -9 $(cat application.pid) # 结束程序
```