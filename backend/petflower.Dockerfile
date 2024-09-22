FROM openjdk:21-jdk-slim
WORKDIR /app
COPY backend/target/petflower-3.3.1.jar .
COPY backend/keystore.p12 .
EXPOSE 443
CMD java -jar /app/petflower-3.3.1.jar