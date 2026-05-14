# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Tech Stack

- **Language:** Kotlin 2.2.21, JVM 24
- **Framework:** Spring Boot 4.0.6 with Spring WebFlux (fully reactive, non-blocking)
- **Database:** PostgreSQL via Spring Data R2DBC (reactive) + Flyway migrations
- **Security:** Spring Security with JWT (jjwt 0.12.6), stateless Bearer token authentication
- **Mapping:** MapStruct 1.6.3 (compile-time, Spring component model)
- **Testing:** JUnit 5, Testcontainers (PostgreSQL auto-provisioned via Docker)

## Commands

```bash
./gradlew bootRun                        # Run the application
./gradlew build                          # Build the project
./gradlew test                           # Run all tests
./gradlew test --tests "FullClassName"   # Run a single test class
./gradlew clean build                    # Clean rebuild
./gradlew bootTestRun                    # Dev mode with auto-provisioned test DB (no Docker setup needed)
docker-compose up                        # Start PostgreSQL for local development
```

## Architecture

Single-module Spring Boot reactive application with a strict layered structure.

```
config/       — Spring beans: SecurityConfig (WebFlux security + JWT filter), AppConfig (BCrypt), R2dbcConfig
security/     — JwtService (token gen/validation), JwtAuthenticationFilter (WebFilter)
controller/   — WebFlux HTTP handlers + dto/ (request/response DTOs)
service/      — Business logic with @Transactional
repository/   — Spring Data R2DBC reactive repositories
mapper/       — MapStruct mappers (Entity ↔ Model ↔ DTO)
domain/
  entity/     — R2DBC database entities (UUID PKs)
  model/      — Domain model classes
```

**Database schema** (defined in `src/main/resources/db/migration/V1__init_schema.sql`):
- `user_account` — users with bcrypt-hashed passwords
- `group_member` — badminton groups owned by a user
- `permission` — roles: `GLOBAL_ADMIN`, `GROUP_ADMIN`, `USER`

**Key architectural decisions:**
- All I/O must use reactive types (`Mono<T>`, `Flux<T>`) or `suspend` functions with `kotlinx-coroutines-reactor`
- Database access is fully reactive via R2DBC — no blocking JDBC calls in the hot path (`spring-jdbc` is included only for Flyway's migration runner)
- Flyway migrations go in `src/main/resources/db/migration/` named `V{n}__{description}.sql`
- MapStruct component model is `spring` — mappers are Spring beans injected via `@Autowired`
- JWT secret and expiration are configured in `application.yml` under the `jwt:` key

**Testing pattern:**
- Tests use `@SpringBootTest` + `@Import(TestcontainersConfiguration::class)` — auto-starts a PostgreSQL 16-alpine container, no manual Docker setup needed
- `TestBadmintonShareGroupApplication` is the dev-mode entry point that also spins up the test container