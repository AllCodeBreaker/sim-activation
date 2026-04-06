# Use a lightweight OpenJDK 17 base image
FROM eclipse-temurin:17-jdk-jammy

# Set a working directory inside the container
WORKDIR /app

# Copy Maven project files
COPY pom.xml .
COPY database_setup.sql .
COPY src ./src

# Build the project with Maven inside the container
# This will create the jar file in target/
RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean package -DskipTests

# Expose the port your Spring Boot app runs on`
EXPOSE 8080

# Set the entrypoint to run the jar
CMD ["java", "-jar", "target/SimActivationPortal-0.0.1-SNAPSHOT.jar"]