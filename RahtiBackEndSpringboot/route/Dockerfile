# Use a JDK to build
# Use a JDK 21 to build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use a minimal JRE 21 for runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY decision1.json /app/decision1.json

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
