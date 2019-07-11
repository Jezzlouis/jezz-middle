## xxl-job 部署

### 调度中心部署

github上面clone 下来 https://github.com/xuxueli/xxl-job

1.首先是application.properties

   <label style="color:red">
    server.port=7755 <br>
   </label>
   
    server.context-path=/xxl-job-admin
    
    ### resources
    spring.mvc.static-path-pattern=/static/**
    spring.resources.static-locations=classpath:/static/
    
    ### freemarker
    spring.freemarker.templateLoaderPath=classpath:/templates/
    spring.freemarker.suffix=.ftl
    spring.freemarker.charset=UTF-8
    spring.freemarker.request-context-attribute=request
    spring.freemarker.settings.number_format=0.##########
    
    ### mybatis
    mybatis.mapper-locations=classpath:/mybatis-mapper/*Mapper.xml
    
    ### xxl-job, datasource
           
   <label style="color:red">
       spring.datasource.url=jdbc:mysql://localhost:3306/xxl_job?Unicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=true <br>
       spring.datasource.username=root <br>
       spring.datasource.password=rendering2017_test <br>
       spring.datasource.driver-class-name=com.mysql.jdbc.Driver<br>
   </label>
    
    spring.datasource.type=org.apache.tomcat.jdbc.pool.DataSource
    spring.datasource.tomcat.max-wait=10000
    spring.datasource.tomcat.max-active=30
    spring.datasource.tomcat.test-on-borrow=true
    spring.datasource.tomcat.validation-query=SELECT 1
    spring.datasource.tomcat.validation-interval=30000
    
    ### xxl-job email
    
   <label style="color:red">
    spring.mail.host=smtpdm.aliyun.com <br>
    spring.mail.port=465 <br>
    spring.mail.username=xxxxxxxxx <br>
    spring.mail.password=xxxxxxxx <br>
    spring.mail.properties.mail.smtp.auth=true <br>
    spring.mail.properties.mail.smtp.starttls.enable=true <br>
    spring.mail.properties.mail.smtp.starttls.required=true <br>
    spring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory <br>
   </label>
   
    ### xxl-job, access token
    xxl.job.accessToken=
    
    ### xxl-job, i18n (default empty as chinese, "en" as english)
    xxl.job.i18n=
    
2.然后是配置 logback.xml

    <?xml version="1.0" encoding="UTF-8"?>
    <configuration debug="false" scan="true" scanPeriod="1 seconds">
    
        <contextName>logback</contextName>
        // 很重要 这里配错就会报错无法启动,报的就是日志有问题
   <label style="color:red">
        <property name="log.path" value="/opt/xxl-job/xxl-job-admin.log"/>
   </label>
    
        <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
            <encoder>
                <pattern>%d{HH:mm:ss.SSS} %contextName [%thread] %-5level %logger{36} - %msg%n</pattern>
            </encoder>
        </appender>
    
        <appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <file>${log.path}</file>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>${log.path}.%d{yyyy-MM-dd}.zip</fileNamePattern>
            </rollingPolicy>
            <encoder>
                <pattern>%date %level [%thread] %logger{36} [%file : %line] %msg%n
                </pattern>
            </encoder>
        </appender>
    
        <root level="info">
            <appender-ref ref="console"/>
            <appender-ref ref="file"/>
        </root>
    
    </configuration>

3. 然后就是 编译 mvn clean install 
           部署xxl-job-admin下边的包就行(core包和example包不用管) nohup java -jar xxl-job-admin-2.1.1.jar &
           打开网站可以看见 http://localhost:7755/xxl-job-admin/  账号 admin 密码 123456
           
### 执行器部署

1.引入maven依赖 (如果依赖有netty包可能会造成netty包冲突,报错的时候调试下源码选择版本最高的那个包解决冲突即可)
    
    <dependency>
        <groupId>com.xuxueli</groupId>
        <artifactId>xxl-job-core</artifactId>
        <version>2.1.0</version>
    </dependency>
    
2. XxlJobConfig文件
    
    不需要修改直接复制使用
    
3. JobHandler (不能有名字重复,重复会报错)
    
    java的话不需要选择GLUE模式，全都选bean模式 使用示例 DemoJobHandler    
    
4. 配置网站的Handler 注意和代码的JobHandler保持一致

5. 配置日志 logback.xml不用变

6. 配置 application.properties

注意是application.properties 里面的值   xxl.job.executor.logpath = xxx 这个路径一定要能找到并且有权限访问,否则报错file找不到

xxl.job.executor.port = xxxx 注意端口不能跟server.port端口一样，且不能跟其他执行器有冲突, 否则会报错

    # web port
    server.port=8081
    
    # log config
    logging.config=classpath:logback.xml
    
    
    ### xxl-jobhandler admin address list, such as "http://address" or "http://address01,http://address02"
    xxl.job.admin.addresses=http://10.60.197.85:7755/xxl-job-admin
    
    ### xxl-jobhandler executor address
    xxl.job.executor.appname=xxl-job-executor
    xxl.job.executor.ip=
    xxl.job.executor.port=7001
    
    ### xxl-jobhandler, access token
    xxl.job.accessToken=
    
    ### xxl-jobhandler log path
    xxl.job.executor.logpath=/Users/jezzlouis/xxl-job/jobhandler
    ### xxl-jobhandler log retention days
    xxl.job.executor.logretentiondays=-1
 
 7. 写代码的时候 使用 XxlJobLogger.log 就能把 日志 在 网站页面的执行日志 打印出来(不用就没有)   