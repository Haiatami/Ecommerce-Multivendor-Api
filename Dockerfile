FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Ecommerce-Multivendor-Api-0.0.1-SNAPSHOT.jar Ecommerce-Multivendor-Api.jar
EXPOSE 5454
ENTRYPOINT [ "java", "-jar", "Ecommerce-Multivendor-Api.jar" ]