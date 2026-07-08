FROM ubuntu:latest as BUILD

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM eclipse-temurin:17-jdk-jammy

EXPOSE 8080

COPY --from=build /target/school.back-0.0.4-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]