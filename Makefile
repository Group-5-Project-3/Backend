# Variables
IMAGE_NAME = project3
IMAGE_TAG := $(shell date +%Y%m%d%H%M%S)  # Timestamp generated once
HEROKU_APP_NAME = cst438project3
HEROKU_REGISTRY = registry.heroku.com/$(HEROKU_APP_NAME)/web

# Default target
all: build tag push release open

# Build the Docker image (includes Maven build)
build:
	echo "Building Maven package..."
	mvn clean package -Dmaven.test.skip=true  # Add Maven build step
	echo "Building Docker image for $(IMAGE_NAME):$(IMAGE_TAG)..."
	docker build --no-cache --platform linux/amd64 -t $(IMAGE_NAME):$(IMAGE_TAG) .


# Tag the Docker image
tag:
	echo "Tagging Docker image..."
	docker tag $(IMAGE_NAME):$(IMAGE_TAG) $(HEROKU_REGISTRY)

# Push the Docker image to Heroku
push:
	echo "Pushing Docker image to Heroku..."
	docker push $(HEROKU_REGISTRY)

# Release the Docker image on Heroku and restart the app
release:
	echo "Releasing Docker image on Heroku..."
	heroku container:release web --app $(HEROKU_APP_NAME)

# Open the Heroku app
open:
	echo "Opening Heroku app..."
	heroku open --app $(HEROKU_APP_NAME)

# Clean up (optional)
clean:
	echo "Cleaning up..."
	docker rmi $(HEROKU_REGISTRY) || true

