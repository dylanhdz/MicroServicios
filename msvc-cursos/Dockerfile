# Dockerfile for msvc-cursos
FROM openjdk:22-jdk

VOLUME /tmp
COPY target/msvc-cursos-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]