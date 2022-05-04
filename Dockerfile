# syntax=docker/dockerfile:1
FROM openjdk:17
WORKDIR /app
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#COPY src ./src
COPY . /app
#RUN ./mvnw dependency:go-offline
CMD ["./mvnw", "spring-boot:run"]