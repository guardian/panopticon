name := """scala-play-react-typescript-starter"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  ws,
  ehcache, //https://www.playframework.com/documentation/2.7.x/JavaWS#Adding-WS-to-project
  guice,
  "com.google.api-client" % "google-api-client" % "1.28.0",
  "com.google.apis" % "google-api-services-drive" % "v3-rev157-1.25.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test,
  "com.beachape" %% "enumeratum-play-json" % "1.5.16",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
