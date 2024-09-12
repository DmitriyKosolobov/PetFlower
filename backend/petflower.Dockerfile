FROM openjdk:21-jdk-slim
WORKDIR /app
COPY backend/target/petflower.jar .
EXPOSE 8080
CMD java -jar /app/petflower.jar