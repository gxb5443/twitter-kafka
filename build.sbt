
lazy val commonSettings = Seq(
  name := "twitterstream",
  version := "1.0",
  organization := "dv",
  scalaVersion := "2.12.1"
)

lazy val twitterstream = (project in file("twitterstream"))
  .settings(commonSettings: _*)
  .settings(
    assemblyJarName in assembly := "twitterstream.jar"
  )
  .settings(libraryDependencies ++= Seq(
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.6",
    "com.twitter" %% "util-core" % "6.40.0" ,
    "com.twitter" % "hbc-core" % "2.2.0",
    "org.apache.kafka" % "kafka-streams" % "0.10.0.1",
    "com.google.code.gson" % "gson" % "2.8.0",
    "com.typesafe" % "config" % "1.3.1",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
))
