FROM hub.c.163.com/library/java:8-jre
MAINTAINER jezz
ADD target/*.jar app.jar
VOLUME /tmp
EXPOSE 9000
ENTRYPOINT ["java","-jar","app.jar"]