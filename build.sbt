name := "constraints"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
    "org.choco-solver" % "choco-solver" % "4.0.0",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
)
