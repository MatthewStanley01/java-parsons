FROM openjdk:8-jdk-alpine
COPY webapp/target/webapp-1.0-SNAPSHOT-exec.jar /app.jar
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.jar"]
