#Step 1: Build stage
FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src

#Resolve dependencies and cache them before copying the source to speed up builds
RUN mvn dependency:go-offline
RUN mvn package -DskipTests

#Step 2: Package stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/inventory_manager-0.0.1-SNAPSHOT.jar inventory_manager.jar
ENTRYPOINT ["java","-jar","inventory_manager.jar"]