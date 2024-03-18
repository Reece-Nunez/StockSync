FROM openjdk:17

WORKDIR /app

COPY /target/inventory_manager-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "inventory_manager-0.0.1-SNAPSHOT.jar"]