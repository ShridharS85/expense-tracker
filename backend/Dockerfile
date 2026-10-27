# ================================
# BUILD STAGE
# ================================

# Use Maven and Java 21 to build the Spring Boot application
FROM maven:3.9-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy all backend project files
COPY . .

# Compile the code and generate the JAR without running tests
RUN mvn clean package -DskipTests


# ================================
# RUNTIME STAGE
# ================================

# Use Java 21 runtime to run the application
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the generated JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Document the application port
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]