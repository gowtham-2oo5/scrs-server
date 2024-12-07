# Step 1: Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Add the Spring Boot application JAR file to the container
# Ensure the JAR file is built beforehand using Maven/Gradle
COPY target/*.jar app.jar

# Step 4: Expose the port the application will run on (default is 8080)
EXPOSE 8080

# Step 5: Set the default command to run the application
CMD ["java", "-jar", "app.jar"]
