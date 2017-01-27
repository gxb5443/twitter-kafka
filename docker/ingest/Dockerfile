FROM anapsix/alpine-java
MAINTAINER Programmatic <ppdev@doubleverify.com>

COPY application.conf /application.conf
COPY twitterstream.jar /twitterstream.jar

CMD ["java", "-cp", "twitterstream.jar", "dv.twitterstream.IngestApp"]
