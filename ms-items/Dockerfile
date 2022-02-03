FROM openjdk:11.0-slim
VOLUME /tmp
EXPOSE 9000
ADD ./target/ms-items-0.0.1-SNAPSHOT.jar ms-items.jar
ENTRYPOINT ["java","-jar","/ms-items.jar"]