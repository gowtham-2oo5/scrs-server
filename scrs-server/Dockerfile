# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar file into the container
COPY target/scrs-server-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (default Tomcat port is 8080)
EXPOSE 8080

# Set the command to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

# docker build -t scrs-server .
# docker run -p 2000:8080 scrs-server
# docker tag scrs-server gowtham2oo5/scrs-server
# docker push gowtham2oo5/scrs-server

