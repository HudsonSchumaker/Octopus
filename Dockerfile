# Description: Dockerfile for the Octopus application
# Author: Hudson Schumaker

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY . .

# Set environment variables
ENV JWT_SECRET=testSecret

# Expose ports, 80 for the application and 5005 for debugging
EXPOSE 80 5005

# Run the application
CMD ["java", "-jar", "build/libs/Octopus-1.0.0.jar", "-env=local"]
