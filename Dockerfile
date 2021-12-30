FROM azul/zulu-openjdk-alpine:11
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=target/TFG-streaming-user-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]