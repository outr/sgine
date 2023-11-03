name := "sgine"
ThisBuild / organization := "org.sgine"
ThisBuild / version := "2.0.0b5-SNAPSHOT4"

val scala213 = "2.13.12"

val scala3 = "3.3.1"

ThisBuild / scalaVersion := scala213
ThisBuild / crossScalaVersions := List(scala213, scala3)
ThisBuild / scalacOptions ++= Seq("-unchecked", "-deprecation")

ThisBuild / resolvers ++= Resolver.sonatypeOssRepos("releases")
ThisBuild / resolvers ++= Resolver.sonatypeOssRepos("snapshots")
ThisBuild / resolvers += "jitpack" at "https://jitpack.io"
ThisBuild / resolvers += "Google" at "https://maven.google.com/"

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
  Developer(id="darkfrog", name="Matt Hicks", email="matt@matthicks.com", url=url("https://matthicks.com"))
)

ThisBuild / fork := true

val gdxVersion: String = "1.12.1"
val reactifyVersion: String = "4.1.0"
val fabricVersion: String = "1.12.6"
val scribeVersion: String = "3.12.2"
val shapedrawerVersion: String = "2.6.0"
val media4sVersion: String = "1.0.21"

val scalaXMLVersion = "2.0.0-M2"
val androidVersion = "4.1.1.4"
val vlcjVersion = "3.10.1"
val scalaTestVersion = "3.2.12"

lazy val root = project.in(file("."))
  .aggregate(core, lwjgl, android, ios, examples)
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
      "com.outr" %% "reactify" % reactifyVersion,
      "org.typelevel" %% "fabric-reactify" % fabricVersion,
      "com.outr" %% "scribe-slf4j" % scribeVersion,
      "space.earlygrey" % "shapedrawer" % shapedrawerVersion,
      "com.outr" %% "media4s" % media4sVersion
    )
  )

lazy val lwjgl = project.in(file("lwjgl"))
  .settings(
    name := "sgine-lwjgl",
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-desktop",
      "com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion classifier "natives-desktop",
      "com.badlogicgames.gdx" % "gdx-backend-lwjgl3" % gdxVersion
    )
  )
  .dependsOn(core)

lazy val android = project.in(file("android"))
  .settings(
    name := "sgine-android",
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx-backend-android" % gdxVersion,
//      "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-armeabi-v7a",
//      "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-arm64-v8a",
//      "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-x86",
//      "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-x86_64",
//      "com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion classifier "natives-armeabi-v7a",
//      "com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion classifier "natives-arm64-v8a",
//      "com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion classifier "natives-x86",
//      "com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion classifier "natives-x86_64",
//      "com.google.android" % "android" % androidVersion % "provided"
    )
  )
//  .dependsOn(core)

lazy val ios = project.in(file("ios"))
  .settings(
    name := "sgine-ios",
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx-backend-robovm" % gdxVersion
    )
  )
  .dependsOn(core)

lazy val examples = project.in(file("examples"))
  .settings(
    name := "sgine-examples",
    fork := true,
    libraryDependencies ++= Seq(
//      "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-desktop",
//      "com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion classifier "natives-desktop",
//      "org.scala-lang.modules" %% "scala-xml" % scalaXMLVersion
    )
  )
  .dependsOn(core, lwjgl)