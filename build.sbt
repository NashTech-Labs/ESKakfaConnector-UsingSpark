name := "ESKakfaConnector-UsingSpark"

version := "0.1"

scalaVersion := "2.11.10"




libraryDependencies ++= Seq(

  "org.apache.spark" %% "spark-core" % "1.6.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.elasticsearch" %% "elasticsearch-spark" % "2.3.3",
  "com.sksamuel.elastic4s" % "elastic4s-core_2.11" % "7.1.0",
  "com.typesafe.play" %% "play-json" % "2.6.0-M1",
  "org.apache.kafka" % "kafka-clients" % "2.4.1"
)
