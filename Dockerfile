FROM google/cloud-sdk:alpine

RUN apk add --no-cache openjdk8 maven gradle

RUN gcloud components install app-engine-java
ENV APPENGINE_HOME /google-cloud-sdk
CMD gradle war appengineRun -Dapp.devserver.host="0.0.0.0"