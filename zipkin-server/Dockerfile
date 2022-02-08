FROM openjdk:11.0-slim
VOLUME /tmp
EXPOSE 9411
ADD ./zipkin-server-2.23.4-exec.jar zipkin-server.jar
ENTRYPOINT ["java","-jar","/zipkin-server.jar"]