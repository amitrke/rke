# Roorkee Spring Boot REST APIs
Build [![CircleCI](https://circleci.com/gh/amitrke/rke.svg?style=svg)](https://circleci.com/gh/amitrke/rke)

This repository contains server side Java code for RESTFul API, the API is developed using Spring boot and is designed to be deployed to Google AppEngine.

## Contributing
Feel free to contribute to this project by creating a pull request.

## Build and test
./gradlew appengineRun

## Run using Docker
```
docker build -t rke .
docker volume create --name maven-rep
docker run --rm -it -h localhost -v maven-repo:/root/.m2 -v "$PWD":/source -w /source -p 8080:8080 rke
```

## URLs
Swagger UI: http://localhost:8080/swagger-ui.html

Appengine local console: http://localhost:8080/_ah/admin/

## Developer workspace setup

- Install Java 8+
- Install STS (https://spring.io/tools)
- Setup Lombok (https://projectlombok.org/setup/eclipse)
- Setup Google Cloud SDK - Java (https://cloud.google.com/sdk/docs/#install_the_latest_cloud_sdk_version)
- git clone
- STS > Import Gradle project

## History
MyRoorkee.com was a college project started by 5 students from S.D.C.E.T College Muzaffarnagar - Computer Science Batch of 2002