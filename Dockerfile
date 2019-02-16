FROM google/cloud-sdk:alpine

RUN apk add --no-cache gradle

RUN gcloud components install app-engine-java
ENV APPENGINE_HOME /google-cloud-sdk/platform/google_appengine/google/appengine/tools/java
CMD gradle appengineRun -Dapp.devserver.host="0.0.0.0"