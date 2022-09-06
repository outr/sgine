name := "sgine"
ThisBuild / organization := "org.sgine"
ThisBuild / version := "2.0.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"
ThisBuild / crossScalaVersions := List("2.13.8", "3.2.0")
ThisBuild / scalacOptions ++= Seq("-unchecked", "-deprecation")

ThisBuild / resolvers += Resolver.sonatypeRepo("releases")
ThisBuild / resolvers += Resolver.sonatypeRepo("snapshots")
ThisBuild / resolvers += "jitpack" at "https://jitpack.io"

ThisBuild / publishTo := sonatypePublishToBundle.value
ThisBuild / sonatypeProfileName := "org.sgine"
ThisBuild / licenses := Seq("MIT" -> url("https://github.com/outr/sgine/blob/master/LICENSE"))
ThisBuild / sonatypeProjectHosting := Some(xerial.sbt.Sonatype.GitHubHosting("outr", "sgine", "matt@matthicks.com"))
ThisBuild / homepage := Some(url("https://github.com/outr/sgine"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/outr/sgine"),
    "scm:git@github.com:outr/sgine.git"
  )
)
ThisBuild / developers := List(
  Developer(id="darkfrog", name="Matt Hicks", email="matt@matthicks.com", url=url("http://matthicks.com"))
)

ThisBuild / fork := true

val gdxVersion: String = "1.11.0"
val reactifyVersion: String = "4.0.8"
val scribeVersion: String = "3.8.2"
val shapedrawerVersion: String = "2.5.0"

val youiVersion = "0.14.4"
val scalaXMLVersion = "2.0.0-M2"
val androidVersion = "4.1.1.4"
val vlcjVersion = "3.10.1"
val scalaTestVersion = "3.2.12"

lazy val root = project.in(file("."))
  .aggregate(core)
  .settings(
    publish := {},
    publishLocal := {}
  )

lazy val core = project
  .in(file("core"))
  .settings(
    name := "sgine-core",
    javaOptions ++= Seq("-verbose:gc"),
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx" % gdxVersion,
      "com.badlogicgames.gdx" % "gdx-tools" % gdxVersion,
      "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-desktop",
      "com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion classifier "natives-desktop",
      "com.badlogicgames.gdx" % "gdx-backend-lwjgl3" % gdxVersion,
      "com.outr" %% "reactify" % reactifyVersion,
      "com.outr" %% "scribe-slf4j" % scribeVersion,
      "space.earlygrey" % "shapedrawer" % shapedrawerVersion
    )
  )

//lazy val lwjgl = project.in(file("lwjgl"))
//  .settings(
//    name := "sgine-lwjgl",
//    libraryDependencies ++= Seq(
//      "com.badlogicgames.gdx" % "gdx-backend-lwjgl3" % gdxVersion
//    )
//  )
//  .dependsOn(coreOld)
//
//lazy val jglfw = project.in(file("jglfw"))
//  .settings(
//    name := "sgine-jglfw",
//    libraryDependencies ++= Seq(
//      "com.badlogicgames.gdx" % "gdx-backend-jglfw" % gdxVersion
//    )
//  )
//  .dependsOn(core)
//
//lazy val android = project.in(file("android"))
//  .settings(
//    name := "sgine-android",
//    libraryDependencies ++= Seq(
//      "com.badlogicgames.gdx" % "gdx-backend-android" % gdxVersion,
//      "com.google.android" % "android" % androidVersion % "provided"
//    )
//  )
//  .dependsOn(core)
//
//lazy val ios = project.in(file("ios"))
//  .settings(
//    name := "sgine-ios",
//    libraryDependencies ++= Seq(
//      "com.badlogicgames.gdx" % "gdx-backend-robovm" % gdxVersion
//    )
//  )
//  .dependsOn(core)
//
//lazy val examples = project.in(file("examples"))
//  .settings(
//    name := "sgine-examples",
//    fork := true,
//    libraryDependencies ++= Seq(
//      "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-desktop",
//      "com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion classifier "natives-desktop",
//      "org.scala-lang.modules" %% "scala-xml" % scalaXMLVersion
//    )
//  )
//  .dependsOn(coreOld, lwjgl, video)