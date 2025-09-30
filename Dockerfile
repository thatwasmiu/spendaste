# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies first (cache layer)
COPY pom.xml .
COPY src ./src

# Build the jar (skip tests to speed up)
RUN mvn package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy built jar from builder
COPY --from=builder /app/target/*.jar app.jar

# Railway will inject $PORT dynamically
EXPOSE 8023

ENTRYPOINT ["java", "-jar", "app.jar"]