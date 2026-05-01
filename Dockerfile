FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# copy pom and wrapper first to leverage Docker layer caching
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src ./src
COPY proto ./proto

RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

# copy the built jar from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 9093

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
