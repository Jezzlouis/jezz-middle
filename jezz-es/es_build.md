# docker 安装 es

## 单机版

    docker search elasticsearch

    docker pull elasticsearch（会报错,需要带上版本）
    docker pull elasticsearch:7.2.0
    docker pull docker.elastic.co/elasticsearch/elasticsearch:7.2.0

-p：将docker镜像中的端口号映射宿主机器端口号，宿主机器端口号:docker容器端口号 ，可写多个，如果多个端口号是连续的，可以直接用-连接，如：4560-4600:4560-4600
-v：将docker镜像中的文件映射到宿主机器指定的文件，可以是文件夹，
-v 宿主机文件:容器文件映射后可直接修改宿主机上的文件就可以改变docker中的配置，也可写多个。docker镜像中软件的配置文档默认在/usr/share”/{软件名}下
–name：指定镜像名称,--name 容器名称

    docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms512m -Xmx512m" -e NETWORK_HOST="0.0.0.0"  --name elasticsearch_single docker.elastic.co/elasticsearch/elasticsearch:7.2.0
    docker run -d --name elasticsearch_single -p 9200:9200 -p 9300:9300 -v /opt/software/config:/usr/share/elasticsearch/config -e ES_JAVA_OPTS="-Xms512m -Xmx512m" -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.2.0
部署之后访问 localhost:9200
    
    docker pull mobz/elasticsearch-head:5
    
    docker run -d -p 9100:9100 docker.io/mobz/elasticsearch-head:5
    
访问 localhost:9100 输入 es地址 无法访问，集群未连接，因为有跨域问题
    
    http.cors.enabled: true
    http.cors.allow-origin: "*"
 
## 集群版
 
创建es的挂载目录以及配置文件:
    
    cd  /opt/software
    mkdir elasticsearch
    cd  elasticsearch
    mkdir config
    mkdir node0
    mkdir node1
    mkdir node2
    chmod 777 node0
    chmod 777 node1
    chmod 777 node2
    
config 里面分别放两个配置文件
    
    cd config
    touch node0.yml
    touch node1.yml
    touch node1.yml
    
node01.yml

````    
    cluster.name: elasticsearch-cluster
    node.name: node-0
    node.master: true
    node.data: true
    node.ingest: false
    network.host: 0.0.0.0
    http.port: 9200
    transport.tcp.port: 9300         
    transport.tcp.compress: true
    discovery.zen.ping.unicast.hosts: ["0.0.0.0:9300", "0.0.0.0:9301","0.0.0.0:9302"]
    discovery.zen.minimum_master_nodes: 2
    discovery.zen.ping_timeout: 200s
    discovery.zen.fd.ping_timeout: 200s
    discovery.zen.fd.ping_interval: 30s
    discovery.zen.fd.ping_retries: 6
    http.cors.enabled: true                                     
    http.cors.allow-origin: "*"
    
````  

node02.yml
 
````    
    cluster.name: elasticsearch-cluster
    node.name: node-1
    node.master: true
    node.data: true
    node.ingest: false
    network.host: 0.0.0.0
    http.port: 9201
    transport.tcp.port: 9301         
    transport.tcp.compress: true
    discovery.zen.ping.unicast.hosts: ["0.0.0.0:9300", "0.0.0.0:9301","0.0.0.0:9302"]
    discovery.zen.minimum_master_nodes: 2
    discovery.zen.ping_timeout: 200s
    discovery.zen.fd.ping_timeout: 200s
    discovery.zen.fd.ping_interval: 30s
    discovery.zen.fd.ping_retries: 6
    http.cors.enabled: true                                     
    http.cors.allow-origin: "*"
    
````  
node03.yml

```` 

    cluster.name: elasticsearch-cluster
    node.name: node-2
    node.master: true
    node.data: true
    node.ingest: false
    network.host: 0.0.0.0
    http.port: 9202
    transport.tcp.port: 9302         
    transport.tcp.compress: true
    discovery.zen.ping.unicast.hosts: ["0.0.0.0:9300", "0.0.0.0:9301","0.0.0.0:9302"]
    discovery.zen.minimum_master_nodes: 2
    discovery.zen.ping_timeout: 200s
    discovery.zen.fd.ping_timeout: 200s
    discovery.zen.fd.ping_interval: 30s
    discovery.zen.fd.ping_retries: 6
    gateway.recover_after_nodes: 1
    http.cors.enabled: true                                     
    http.cors.allow-origin: "*"
    http.cors.allow-credentials: true
    
````
    
调高JVM线程数限制数量（不然启动容器的时候会报错，亲身试验）

````
    vim /etc/sysctl.conf
    # 添加这个
    vm.max_map_count=262144 
    # 保存后执行这个命令
    sysctl -p
````
    
初始化容器
    

    docker run -e ES_JAVA_OPTS="-Xms256m -Xmx256m" -d -p 9200:9200 -p 9300:9300 -v /opt/software/elasticsearch/config/node0.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v /opt/software/elasticsearch/node0:/usr/share/elasticsearch/data --name es-node0 elasticsearch:7.2.0
    
    docker run -e ES_JAVA_OPTS="-Xms256m -Xmx256m" -d -p 9201:9201 -p 9301:9301 -v /opt/software/elasticsearch/config/node1.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v /opt/software/elasticsearch/node1:/usr/share/elasticsearch/data --name es-node1 elasticsearch:7.2.0
    
    docker run -e ES_JAVA_OPTS="-Xms256m -Xmx256m" -d -p 9202:9202 -p 9302:9302 -v /opt/software/elasticsearch/config/node2.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v /opt/software/elasticsearch/node2:/usr/share/elasticsearch/data --name es-node2 elasticsearch:7.2.0

查看日志
    
    docker logs -f -t --tail 1000 es-node0
    
安装kibana
    
    docker pull kibana:7.2.0
    
    docker run --link es-node0:elasticsearch -p 5601:5601 --name kibana -d kibana:7.2.0

## Linux yum 安装

安装jdk
yum install java-1.8.0-openjdk* -y

1.下载并安装GPG key

    rpm --import https://artifacts.elastic.co/GPG-KEY-elasticsearch

2.添加yum仓库
    
    cd /etc/yum.repos.d
    
    touch  elasticsearch.repo
    
vim elasticsearch.repo
    
    [elasticsearch-7.x]
    name=Elasticsearch repository for 7.x packages
    baseurl=https://artifacts.elastic.co/packages/7.x/yum
    gpgcheck=1
    gpgkey=https://artifacts.elastic.co/GPG-KEY-elasticsearch
    enabled=1
    autorefresh=1
    type=rpm-md
 
安装elasticsearch
    
    sudo yum install elasticsearch
    
配置elasticsearch
    
    vim /etc/elasticsearch/elasticsearch.yml
    
使用systemd管理elasticsearch服务
    
    sudo /bin/systemctl daemon-reload
    sudo /bin/systemctl enable elasticsearch.service
    sudo systemctl start elasticsearch.service

开放端口
    
    /sbin/iptables -I INPUT -p tcp --dport 9200 -j ACCEPT
    
安装kibana
    
    vim /etc/yum.repos.d/kibana.repo
    
        [kibana-7.x]
        name=Kibana repository for 7.x packages
        baseurl=https://artifacts.elastic.co/packages/7.x/yum
        gpgcheck=1
        gpgkey=https://artifacts.elastic.co/GPG-KEY-elasticsearch
        enabled=1
        autorefresh=1
        type=rpm-md
    
    sudo yum install kibana
    
    sudo /bin/systemctl daemon-reload
    sudo /bin/systemctl enable kibana.service
    
    sudo systemctl start kibana.service
    sudo systemctl stop kibana.service
    
安装 logstash
    
    同上
    sudo /bin/systemctl daemon-reload
    sudo /bin/systemctl enable logstash.service
    sudo systemctl start logstash.service
