FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /target/scrapper.jar .
EXPOSE 8080
CMD java -jar /app/petflower.jar