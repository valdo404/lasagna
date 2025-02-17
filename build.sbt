ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
      name := "lasagna",
      version := "0.1",
      scalaVersion := "3.3.1"
  )
  .aggregate(parser, sqlModel)

lazy val sqlModel = (project in file("lasagna-sql-model"))
  .settings(
        name := "lasagna-sql-model",
        idePackagePrefix := Some("com.lapoule.lasagna.sql"),
  )

lazy val parser = (project in file("lasagna-parser"))
  .dependsOn(sqlModel)
  .settings(
      name := "lasagna-parser",
      idePackagePrefix := Some("com.lapoule.lasagna.parser"),
      libraryDependencies += "com.github.scopt" %% "scopt" % "4.1.0",
      libraryDependencies += "com.lihaoyi" %% "fastparse" % "3.0.2",
      libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.17",
      libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test"
  )