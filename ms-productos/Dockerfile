FROM openjdk:11.0-slim
VOLUME /tmp
ADD ./target/ms-productos-0.0.1-SNAPSHOT.jar ms-productos.jar
ENTRYPOINT ["java","-jar","/ms-productos.jar"]