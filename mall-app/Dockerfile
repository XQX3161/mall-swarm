# Multi-stage build: build jar with Maven, then run with JRE
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /workspace

# copy only pom first for dependency download
COPY pom.xml ./
COPY mall-common/pom.xml ./mall-common/
COPY mall-app/pom.xml ./mall-app/

# copy sources
COPY mall-common ./mall-common
COPY mall-app ./mall-app

RUN mvn -f pom.xml -DskipTests clean package -pl mall-app -am

# runtime image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/mall-app/target/mall-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
