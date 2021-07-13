FROM openjdk:11-jdk-slim
EXPOSE 8080:8080
RUN mkdir /app
COPY . /src
WORKDIR /src
CMD ["./docker"]