
# Use official OpenJDK base image
FROM openjdk:17-slim
LABEL authors="Kim Chansokpheng"


# Set working directory in container
WORKDIR /app

# Copy the jar file into the container
#COPY src/main/java/server/*.jar app.jar

# Set entry point to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# Optional: Include build stage for Maven/Gradle projects
# Uncomment and modify as needed:

# Build stage
#FROM maven:3.8-openjdk-17 AS build
#WORKDIR /build
#COPY pom.xml .
#COPY src src
#RUN mvn clean package -DskipTests

# Run stage
#FROM openjdk:17-slim
#WORKDIR /app
#COPY --from=build /build/target/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]