FROM openjdk:11-jdk-slim

WORKDIR /src
COPY . /src

RUN bash gradlew fatJar

WORKDIR /run
RUN cp /src/build/libs/*.jar /run/server.jar

EXPOSE 8080

CMD java -jar /run/server.jar