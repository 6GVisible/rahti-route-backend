# Use OpenJDK 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy the jar file into the container
COPY target/route-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Spring Boot defaults to 8080)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
