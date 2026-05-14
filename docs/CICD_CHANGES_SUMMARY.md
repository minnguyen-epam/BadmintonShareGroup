# CI/CD Pipeline Setup - Changes Summary

## Overview

Set up a complete CI/CD pipeline using GitHub Actions, with code quality tooling (Spotless, detekt, OWASP), Docker image publishing to AWS ECR, and GitOps deployment to Kubernetes via ArgoCD.

---

## New Files Created

### GitHub Actions Workflows

| File | Purpose |
|------|---------|
| `.github/workflows/feature-branch.yml` | CI pipeline triggered on push to any non-main branch |
| `.github/workflows/main-branch.yml` | CI/CD pipeline triggered on push to main (after PR merge) |

**Feature branch pipeline flow:**
```
Lint (spotless + detekt)  ──┐
Build & Unit Tests        ──┤──→ Integration Tests ──→ Docker Build + Push + Trivy Scan
Dependency Scan (OWASP)   ──┘                          (to ECR, tag: sha-<commit>)
```

**Main branch pipeline flow:**
- Same as feature branch, plus:
  - Tags image with both `sha-<commit>` and `latest`
  - `deploy-dev` job: updates image tag in the infra repo via `kustomize edit set image`
  - ArgoCD auto-syncs the dev environment

### Code Quality Configuration

| File | Purpose |
|------|---------|
| `.editorconfig` | Code formatting rules for ktlint (indent, line length, charset) |
| `detekt.yml` | Static analysis rule overrides (complexity thresholds, style rules) |

### Integration Test Source Set

| File | Purpose |
|------|---------|
| `src/integrationTest/kotlin/.../BadmintonShareGroupApplicationTests.kt` | Moved from `src/test/` - Spring Boot context load test using Testcontainers |
| `src/integrationTest/kotlin/.../TestcontainersConfiguration.kt` | Moved from `src/test/` - Testcontainers PostgreSQL setup |

---

## Modified Files

### `build.gradle.kts`

**Plugins added:**
- `com.diffplug.spotless` (v7.0.4) - Code formatting with ktlint
- `dev.detekt` (v2.0.0-alpha.1) - Kotlin static analysis
- `org.owasp.dependencycheck` (v12.1.1) - Dependency vulnerability scanning

**Dependencies added:**
- `spring-boot-starter-actuator` - Health check endpoints for Kubernetes probes

**Configuration added:**
- `spotless {}` block - ktlint formatting for `src/**/*.kt` and `*.gradle.kts`
- `detekt {}` block - Uses default rules + custom overrides from `detekt.yml`
- `dependencyCheck {}` block - Fails build on CVSS >= 7.0, outputs HTML + SARIF
- Integration test source set (`integrationTest`) with separate `./gradlew integrationTest` task

**Kotlin version change:**
- `2.2.21` -> `2.2.20` (required for detekt 2.0.0-alpha.1 compatibility)

### `Dockerfile`

- Added non-root user (`appuser`) for security best practice
- Added `HEALTHCHECK` instruction for container health monitoring via `/actuator/health`

### `src/main/resources/application.yml`

Added Spring Boot Actuator health probe configuration:
```yaml
management:
  endpoint:
    health:
      probes:
        enabled: true
  health:
    livenessstate:
      enabled: true
    readinessstate:
      enabled: true
```

### `SecurityConfig.kt`

- Added `/actuator/health/**` to permitted endpoints (bypasses JWT authentication)

### Multiple `.kt` files (auto-formatted by Spotless)

The following files were reformatted by `./gradlew spotlessApply` (no logic changes):
- `R2dbcConfig.kt`
- `GroupMemberController.kt`
- `UserAccountController.kt`
- `UserAccountDto.kt`
- `Permission.kt` (domain model)
- `UserAccount.kt` (domain model)
- `GroupMemberResponse.kt`
- `PermissionResponse.kt`
- `UserAccountResponse.kt`
- `JwtProperties.kt`
- `JwtAuthenticationFilter.kt`
- `JwtService.kt`
- `GroupMemberService.kt`
- `UserAccountService.kt`
- `TestBadmintonShareGroupApplication.kt`
- `TestcontainersConfiguration.kt` (in `src/test/`)

---

## Gradle Commands Reference

| Command | Purpose |
|---------|---------|
| `./gradlew spotlessCheck` | Verify code formatting (CI) |
| `./gradlew spotlessApply` | Auto-fix code formatting (local dev) |
| `./gradlew detekt` | Run static analysis |
| `./gradlew test` | Run unit tests only |
| `./gradlew integrationTest` | Run integration tests (requires Docker) |
| `./gradlew dependencyCheckAnalyze` | Scan dependencies for vulnerabilities |
| `./gradlew build` | Full build with unit tests |
| `./gradlew build integrationTest` | Full build with all tests |

---

## Required Setup (Not Yet Done)

### AWS Infrastructure
- Create ECR repository in `ap-southeast-1`
- Create IAM OIDC identity provider for GitHub Actions
- Create IAM role with ECR push permissions (trusted by GitHub OIDC)

### GitHub Repository Secrets
| Secret | Description |
|--------|-------------|
| `AWS_ROLE_ARN` | ARN of the IAM role for GitHub Actions OIDC auth |
| `INFRA_REPO_PAT` | GitHub PAT with write access to the infra repo |

### GitHub Workflow Config
- Update `INFRA_REPO` env var in `main-branch.yml` with actual `org/repo-name`

### Separate Infrastructure Repo (`badminton-share-group-infra`)
Needs to be created with:
- Kustomize base manifests (Deployment, Service, Ingress, ConfigMap)
- Environment overlays (dev, staging, prod)
- SealedSecrets for credentials
- ArgoCD Application and AppProject resources

### Kubernetes Cluster
- ArgoCD installed
- Bitnami SealedSecrets controller installed
- NGINX Ingress controller installed
- Nodes with IAM instance profiles for ECR pull access
