# Roorkee Spring Boot REST APIs
Build [![CircleCI](https://circleci.com/gh/amitrke/rke.svg?style=svg)](https://circleci.com/gh/amitrke/rke)

This repository contains server side Java code for RESTFul API, the API is developed using Spring boot and is designed to be deployed to Google AppEngine.

## Contributing
Feel free to contribute to this project by creating a branch and later a pull request.

## Build and test
./mvnw install

## Run using Docker

docker build -t rke .
docker volume create --name maven-rep
docker run --rm -it -h localhost -v maven-repo:/root/.m2 -v "$PWD":/source -w /source -p 8080:8080 rke

## History
MyRoorkee.com was a college project started by 5 students from S.D.C.E.T College Muzaffarnagar - Computer Science Batch of 2002