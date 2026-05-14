# Stage 1: Build
# Pull eclipse-temurin:24-jdk-alpine as the build environment
FROM eclipse-temurin:24-jdk-alpine AS builder
WORKDIR /app

# Copy Gradle wrapper files into the container
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle.kts settings.gradle.kts ./

# Download dependencies (cached layer)
RUN ./gradlew dependencies --no-daemon || true

# copy your source code, then compile + build the fat JAR
COPY src/ src/
RUN ./gradlew bootJar --no-daemon -x test

# Stage 2: Runtime
# start a NEW smaller image (no compiler, just JRE)
FROM eclipse-temurin:24-jre-alpine
WORKDIR /app

# Copy ONLY the JAR from the builder stage above
COPY --from=builder /app/build/libs/*.jar app.jar

#It does not actually open the port, but it serves as documentation for users of the image
EXPOSE 8080
#The command that runs when the container starts. Equivalent to typing this in the terminal:
 #
 #  java -jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
