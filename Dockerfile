FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENV DB_URL=127.0.0.1:3306 \
    DB_USERNAME=root \
    DB_PASSWORD=1111 \
    FAKE_DATA=false
ENTRYPOINT ["java", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-jar", \
            "/app.jar", \
            "--spring.datasource.url=jdbc:mysql://${DB_URL}/user_management_database?createDatabaseIfNotExist=true&useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true&useSSL=true", \
            "--spring.datasource.username=${DB_USERNAME}", \
            "--spring.datasource.password=${DB_PASSWORD}", \
            "--fakedata=${FAKE_DATA}"]

#FROM gradle:4.8.0-jdk8-alpine
#COPY . /app/
#WORKDIR /app/
#USER root
#RUN gradle build --stacktrace
#CMD java -jar /app/build/libs/user-management-0.0.1-SNAPSHOT.jar