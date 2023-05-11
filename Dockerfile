FROM openjdk:17-slim
ARG JAR_FILE=build/libs/blog-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
