#Get the code
FROM amitrke/java-appengine:latest

WORKDIR /root/src
RUN git clone https://github.com/amitrke/rke.git

#Build
WORKDIR /root/src/rke
RUN mvn -DskipTests=true install

#Run appengine server and datastore

#Open port
