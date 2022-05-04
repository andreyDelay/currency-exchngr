# syntax=docker/dockerfile:1
FROM openjdk:17

WORKDIR /app

COPY . /app
COPY checkstyle.xml ./
RUN ./mvnw dependency:go-offline

CMD ["./mvnw", "spring-boot:run"]