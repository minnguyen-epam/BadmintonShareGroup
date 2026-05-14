IMAGE_NAME = badminton-share-group
IMAGE_TAG  = 0.0.1-SNAPSHOT

.PHONY: build docker-build gradle-image up down logs restart

# Build JAR locally
build:
	./gradlew bootJar --no-daemon -x test

# Solution A: build Docker image via Dockerfile
docker-build:
	docker build -t $(IMAGE_NAME):$(IMAGE_TAG) .

# Solution B: build Docker image via Gradle (Cloud Native Buildpacks, no Dockerfile needed)
gradle-image:
	./gradlew bootBuildImage --no-daemon

# Start full stack (postgres + app). Build the image first with docker-build or gradle-image.
up:
	docker compose up -d

# Stop and remove containers
down:
	docker compose down

# Tail app logs
logs:
	docker compose logs -f app

# Restart only the app container
restart:
	docker compose restart app
