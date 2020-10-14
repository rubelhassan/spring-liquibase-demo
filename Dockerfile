FROM openjdk:8-jdk-alpine

VOLUME /tmp

ARG JAR_FILE
ARG APP_PROP_LOC
RUN echo "App Property Location:" + "$APP_PROP_LOC"
RUN echo "App JAR Location:" + "$JAR_FILE"
COPY ${JAR_FILE} app.jar
COPY ${APP_PROP_LOC} app.properties

ENTRYPOINT "java" \
    "-jar" \
    "-Dspring.config.location=app.properties" \
    "/app.jar"