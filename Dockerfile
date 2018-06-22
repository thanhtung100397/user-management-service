#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

FROM gradle:4.8.0-jdk8-alpine
VOLUME /app
COPY . /app
WORKDIR /app
USER root
RUN gradle build --stacktrace
CMD java -jar build/libs/user-managerment-0.0.1-SNAPSHOT.jar