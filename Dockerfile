#1 USING MULTI-STAGE BUILD
#FROM gradle:4.8.0-jdk8-alpine AS GRADLE_BUILD_IMAGE
#USER root
#WORKDIR /app/
#COPY build.gradle gradlew gradlew.bat settings.gradle gradle /app/
#RUN gradle dependencies --stacktrace
#COPY src /app/src
#RUN gradle build --stacktrace
#
#FROM openjdk:8-jre-alpine
#WORKDIR /jar/
#COPY --from=GRADLE_BUILD_IMAGE /app/build/libs/user-management-0.0.1-SNAPSHOT.jar app.jar
#ENV DB_URL=127.0.0.1:3306 \
#    DB_NAME=user_management_database \
#    DB_USERNAME=root \
#    DB_PASSWORD=1111 \
#    FAKE_DATA=false
#CMD ["java", \
#     "-Djava.security.egd=file:/dev/./urandom", \
#     "-jar", \
#     "/jar/app.jar", \
#     "--spring.datasource.url=jdbc:mysql://${DB_URL}/${DB_NAME}?createDatabaseIfNotExist=true&useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true&useSSL=true", \
#     "--spring.datasource.username=${DB_USERNAME}", \
#     "--spring.datasource.password=${DB_PASSWORD}", \
#     "--fakedata=${FAKE_DATA}"]



#2. USING GRADLE PLUGIN DOCKER
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENV DB_URL=127.0.0.1:3306 \
    DB_NAME=user_management_database \
    DB_USERNAME=root \
    DB_PASSWORD=1111 \
    FAKE_DATA=false \
    CONSUL_HOST=127.0.0.1 \
    CONSUL_PORT=8500
CMD ["java", \
     "-Djava.security.egd=file:/dev/./urandom", \
     "-jar", \
     "/app.jar", \
     "--spring.datasource.url=jdbc:mysql://${DB_URL}/${DB_NAME}?createDatabaseIfNotExist=true&useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true", \
     "--spring.datasource.username=${DB_USERNAME}", \
     "--spring.datasource.password=${DB_PASSWORD}", \
     "--fakedata=${FAKE_DATA}", \
     "--spring.cloud.consul.host=${CONSUL_HOST}", \
     "--spring.cloud.consul.port=${CONSUL_PORT}"]

#3. LOAD DEPENDENCY, BUILD THEN CREATE IMAGE FROM ARTIFACT
#FROM gradle:4.8.0-jdk8-alpine
#COPY . /app/
#WORKDIR /app/
#USER root
#RUN gradle build --stacktrace
#ENV DB_URL=127.0.0.1:3306 \
#    DB_NAME=user_management_database
#    DB_USERNAME=root \
#    DB_PASSWORD=1111 \
#    FAKE_DATA=false
#ENTRYPOINT ["java", \
#            "-jar", \
#            "/app/build/libs/user-management-0.0.1-SNAPSHOT.jar", \
#            "--spring.datasource.url=jdbc:mysql://${DB_URL}/${DB_NAME}?createDatabaseIfNotExist=true&useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true&useSSL=true", \
#            "--spring.datasource.username=${DB_USERNAME}", \
#            "--spring.datasource.password=${DB_PASSWORD}", \
#            "--fakedata=${FAKE_DATA}"]