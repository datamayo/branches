FROM eclipse-temurin:17-jre-focal
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} branches.jar
ENTRYPOINT ["java", "-jar", "/branches.jar"]