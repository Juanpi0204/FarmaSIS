
# Etapa 1: Build del Jar
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn -B package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar

WORKDIR /app
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]
