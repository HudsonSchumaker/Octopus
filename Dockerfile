FROM openjdk:21-jdk-slim
WORKDIR /app
COPY . .
EXPOSE 80

# Run the application
CMD ["java", "-jar", "build/libs/Octopus-1.0-SNAPSHOT.jar", "-env=local"]