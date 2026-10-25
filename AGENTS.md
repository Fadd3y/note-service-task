# Repository Guidelines

## Project Structure & Module Organization

This is a Java 17 Spring Boot note service. Production code is under `src/main/java/ru/project/noteservicetask`, grouped by responsibility: `controller`, `service`, `repository`, `mapper`, `dto`, `entity`, and `exception`. Runtime settings live in `src/main/resources/application.properties`. Tests mirror the package structure under `src/test/java`; add service behavior tests beside the service implementation tests. The root `Dockerfile` packages the application.

## Build, Test, and Development Commands

Use the Maven wrapper so the project’s configured Maven version is used:

- `./mvnw clean package` (Windows: `mvnw.cmd clean package`) compiles, runs tests, and builds the executable artifact in `target/`.
- `./mvnw test` (Windows: `mvnw.cmd test`) runs the test suite.
- `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`) starts the service locally.
- `docker build -t note-service .` builds the container; `docker run --rm -p 8080:8080 note-service` runs it.

## Coding Style & Naming Conventions

Follow the existing Java package layout and standard Java naming: types in `PascalCase`, methods and fields in `camelCase`, constants in `UPPER_SNAKE_CASE`. Keep one public type per file and put request/response models in the corresponding `dto` subpackage. Use four spaces for indentation and keep controller logic focused on HTTP mapping; put business behavior in services and storage operations in repositories. No formatter or linter is configured in `pom.xml`, so match surrounding files.

## Testing Guidelines

Tests use the Spring Boot test starter and JUnit. Name test classes `*Test` and test methods for the behavior they verify. Cover success paths and relevant validation, filtering, and not-found cases. Run `./mvnw test` before submitting changes.

## Commit & Pull Request Guidelines

The available history contains only `init commit`, `Create README.md`, and `repository fix`, which is insufficient to infer a strict commit format. Use a short, imperative summary that names the change. PRs should explain behavior and rationale, list verification commands, and include request/response examples for API changes; link an issue when one exists.

## Security & Configuration

Keep environment-specific values and secrets out of source control. Configure runtime settings through `application.properties` or deployment environment, and avoid logging note contents or credentials.
