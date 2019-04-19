## docker :

  >  
    FROM       设置镜像使用的基础镜像
    MAINTAIER  设置镜像的作者
    RUN        编译镜像时运行的脚本
    CMD        设置容器的启动命令
    LABEL      设置镜像的标签
    EXPOESE    设置镜像的端口
    ENV        设置容器的环境变量
    ADD        编译镜像时复制文件到镜像中
    COPY       编译镜像时复制文件到镜像中
    ENTRYPOINT 设置容器的入口程序
    VOLUME     设置容器的挂载卷
    USER       设置运行RUN CMD ENTRYPOINT的用户名
    WORKDIR    设置RUN CMD ENTRYPOINT ADD COPY 的工作目录
    ARG        设置编译镜像时加入的参数
    ONBUILD    设置镜像的ONBUILD命令
    STOPSIGNAL 设置容器的退出信号量
    
### docker 安装: 

   [参照官网docker安装](https://docs.docker.com)
   
   ![Image text](https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/docker/docker_1.png)
   
   1. 卸载老版本
   
    sudo yum remove docker \
                   docker-client \
                   docker-client-latest \
                   docker-common \
                   docker-latest \
                   docker-latest-logrotate \
                   docker-logrotate \
                   docker-engine

   2. 下载docker CE 
   
   2.1 下载需要的工具包
   
    sudo yum install -y yum-utils \
      device-mapper-persistent-data \
      lvm2

   2.2 设置仓库
    
    sudo yum-config-manager \
        --add-repo \
        https://download.docker.com/linux/centos/docker-ce.repo
   
   2.3 下载docker ce(默认最新版)
   
    sudo yum install docker-ce docker-ce-cli containerd.io
    
   3 开始docker(等了半分钟)
    
    sudo systemctl start docker
    
   4 运行测试镜像
   
    sudo docker run hello-world
    
### 创建idea项目

   项目最终整体结构:
   
   ![Image text](https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/docker/docker_idea_final.png)
    
   1.pom文件添加:
   
   ![Image text](https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/docker/docker_pom.png)
   
    <properties>
            <java.version>1.8</java.version>
            <docker.image.prefix>jezz</docker.image.prefix>
    </properties>
        
    <plugin>
        <groupId>com.spotify</groupId>
        <artifactId>docker-maven-plugin</artifactId>
        <version>1.2.0</version>
        <configuration>
            <imageName>${docker.image.prefix}/${project.artifactId}</imageName>
            <dockerDirectory></dockerDirectory>
            <resources>
                <resource>
                    <targetPath>/</targetPath>
                    <directory>${project.build.directory}</directory>
                    <include>${project.build.finalName}.jar</include>
                </resource>
            </resources>
        </configuration>
    </plugin>
    
   2.controller
    
    @RestController
    public class DockerController {
    
        @RequestMapping("/docker")
        @ResponseBody
        String docker(String name) {
            return "Hello Docker !!!   Hello K8s !!!" ;
        }
    }
    
   3.main
    
    @ComponentScan(basePackages = {"com.jezz.docker"})
    @SpringBootApplication
    public class JezzDockerApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(JezzDockerApplication.class, args);
            System.out.println("docker web start ...");
        }
    
    }
    
   4.application.properties
    
    server.port=9000
    
   5. 启动项目
   
   ![Image text](https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/docker/docker_start_1.png)
   
   6. 访问(localhost:9000/docker)
  
   ![Image text](https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/docker/docker_access_1.png)
   
### idea 安装docker插件

   ![Image text](https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/docker/docker_idea_1.png)
   
        
### docker idea一键部署 : 无 CA 认证  

   1. 修改服务器配置，开放Docker的远程连接访问
   
    vim /usr/lib/systemd/system/docker.service 
    
   2. 将ExecStart属性value值改为
     
    /usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock

   3. 重启docker
   
    systemctl daemon-reload
    systemctl restart docker
    
   4. 开放防火墙2375端口
    
    /sbin/iptables -I INPUT -p tcp --dport 2375 -j ACCEPT
    iptables-save
    
   5. 配置idea上的docker
      
   ![Image text](https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/docker/docker_idea_2.png)
    
   6. 新建Dockerfile在项目src目录下
   
     FROM hub.c.163.com/library/java:8-jre
     MAINTAINER jezz
     ADD target/*.jar app.jar
     VOLUME /tmp
     EXPOSE 9000
     ENTRYPOINT ["java","-jar","app.jar"]
       
   7. 配置run configration
      
   ![Image text](https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/docker/docker_idea_3.png)
      
   8. 运行容器
      
   ![Image text](https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/docker/docker_running.png)
      
   9. 发布容器
      
   ![Image text](https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/docker/docker_deploy_1.png)
      
   10. 访问 172.16.4.52:9000/docker (我的docker放在虚拟机172.16.4.52上)
      
   ![Image text](https://github.com/Jezzlouis/jezz-middle/blob/master/jezz-images/images/docker/docker_access_2.png)
    
### docker idea一键部署 : 有 CA 认证 (未完待续)
  
  
  
  



## 参考文献 : 

   [https://blog.csdn.net/ChineseYoung/article/details/83107353](https://blog.csdn.net/ChineseYoung/article/details/83107353) 
    
   [https://blog.csdn.net/lovexiaotaozi/article/details/82797236](https://blog.csdn.net/lovexiaotaozi/article/details/82797236)
   
   [https://my.oschina.net/wuweixiang/blog/2874064](https://my.oschina.net/wuweixiang/blog/2874064)
  