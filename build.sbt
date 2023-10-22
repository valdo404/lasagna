ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "fastparse-sql",
    idePackagePrefix := Some("com.lapoule.fastparse.sql"),
    libraryDependencies += "com.lihaoyi" %% "fastparse" % "3.0.2"
  )
