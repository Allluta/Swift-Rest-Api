# Build stage
FROM openjdk:17-jdk-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the required Excel file to the container for later use
COPY src/main/resources/Interns_2025_SWIFT_CODES.xlsx /app/src/main/resources/Interns_2025_SWIFT_CODES.xlsx

# Copy Maven wrapper files and the pom.xml for Maven build
COPY mvnw mvnw.cmd pom.xml .
COPY .mvn .mvn

# Ensure the Maven wrapper script has execution permissions
RUN chmod +x mvnw

# Download dependencies to go offline (optimize Maven usage during build)
RUN ./mvnw dependency:go-offline

# Copy the rest of the project files into the container
COPY . .

# Build the application and create the .jar file (skip tests to speed up the build)
RUN ./mvnw clean package -DskipTests

# Final image stage
FROM openjdk:17-jdk-slim

# Set the working directory for the final image
WORKDIR /app

# Copy the compiled .jar file from the build stage into the final image
COPY --from=build /app/target/swift-rest-api-0.0.1-SNAPSHOT.jar /app/swift-rest-api.jar

# Copy the SWIFT codes Excel file into the final image
COPY --from=build /app/src/main/resources/Interns_2025_SWIFT_CODES.xlsx /app/src/main/resources/Interns_2025_SWIFT_CODES.xlsx

# Expose port 8080 for the application to listen on
EXPOSE 8080

# Set the command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "/app/swift-rest-api.jar"]
