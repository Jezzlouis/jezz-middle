# kafka

docker 安装 kafka
1. docker search kafka 查看有哪些镜像可用。
2. docker pull wurstmeister/kafka
3. 
    
        docker run -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=127.0.0.1:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://127.0.0.1:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka:latest
        docker run -d --name kafka -p 9092:9092 --link zookeeper --env KAFKA_ZOOKEEPER_CONNECT=127.0.0.1:2181 --env KAFKA_ADVERTISED_HOST_NAME=127.0.0.1 --env KAFKA_ADVERTISED_PORT=9092 --volume /etc/localtime:/etc/localtime wurstmeister/kafka:latest
        