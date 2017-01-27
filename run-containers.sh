#!/bin/sh

function dockerInstalled() {
  if ! command -v docker &> /dev/null ; then
    echo "Docker not installed"
    exit 1
  fi
}

function sbtInstalled() {
  if ! command -v sbt &> /dev/null ; then
    echo "SBT not installed"
    exit 1
  fi
}

function clearContainers() {
  docker rm -f ingest
  docker rm -f aggregation
  docker rm -f kafka
  docker rm -f zookeeper
}

function clearImages() {
  docker rmi twitterstream_ingest
  docker rmi twitterstream_aggregation
}

dockerInstalled
sbtInstalled

sbt twitterstream/assembly

clearContainers
clearImages

cp application.conf docker/ingest/
cp twitterstream/target/scala-2.12/twitterstream.jar docker/ingest/twitterstream.jar
cp application.conf docker/aggregation/
cp twitterstream/target/scala-2.12/twitterstream.jar docker/aggregation/twitterstream.jar

docker-compose up --force-recreate
