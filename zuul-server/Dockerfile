FROM openjdk:11.0-slim
VOLUME /tmp
EXPOSE 8089
ADD ./target/zuul-server-0.0.1-SNAPSHOT.jar zuul-server.jar
ENTRYPOINT ["java","-jar","/zuul-server.jar"]