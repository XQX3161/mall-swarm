# mall-swarm (updated)

This repository contains a minimal runnable structure for the mall-swarm example application using Java 17 and Spring Boot 2.7.12.

Main changes made on branch `fix/run-project-java17`:
- Convert project to a multi-module Maven build with modules: `mall-common`, `mall-app`.
- Upgrade Java version to 17 and set compiler compliance.
- Move common classes into `mall-common` module.
- Add `mall-app` module containing the Spring Boot application.
- Add Dockerfile and docker-compose for one-command startup (MySQL + app).
- Add example DB init SQL and application.yml for configuration.

Quick start (requires Docker & Docker Compose):

1. Build and start services:

   docker-compose up --build

2. The application will be available at http://localhost:8080

3. To build locally with Maven (Java 17 required):

   mvn -U -DskipTests clean package
   java -jar mall-app/target/mall-app-0.0.1-SNAPSHOT.jar

Notes:
- Default MySQL credentials used in docker-compose: root / password (change in production).
- If you prefer a single-module layout, I can instead merge modules — tell me which option you prefer.
