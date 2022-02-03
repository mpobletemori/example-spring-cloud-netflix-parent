FROM openjdk:11.0-slim
VOLUME /tmp
ADD ./target/ms-oauth2-authserver-0.0.1-SNAPSHOT.jar ms-oauth2-authserver.jar
ENTRYPOINT ["java","-jar","/ms-oauth2-authserver.jar"]