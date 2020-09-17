name := "sgine"
organization in ThisBuild := "org.sgine"
version in ThisBuild := "2.0.0-SNAPSHOT"
scalaVersion in ThisBuild := "2.13.3"
crossScalaVersions in ThisBuild := List("2.13.3", "2.12.12")
scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation")
resolvers in ThisBuild += Resolver.sonatypeRepo("releases")
resolvers in ThisBuild += Resolver.sonatypeRepo("snapshots")

publishTo in ThisBuild := sonatypePublishToBundle.value
sonatypeProfileName in ThisBuild := "org.sgine"
publishMavenStyle in ThisBuild := true
licenses in ThisBuild := Seq("MIT" -> url("https://github.com/outr/sgine/blob/master/LICENSE"))
sonatypeProjectHosting in ThisBuild := Some(xerial.sbt.Sonatype.GitHubHosting("outr", "sgine", "matt@matthicks.com"))
homepage in ThisBuild := Some(url("https://github.com/outr/sgine"))
scmInfo in ThisBuild := Some(
  ScmInfo(
    url("https://github.com/outr/sgine"),
    "scm:git@github.com:outr/sgine.git"
  )
)
developers in ThisBuild := List(
  Developer(id="darkfrog", name="Matt Hicks", email="matt@matthicks.com", url=url("http://matthicks.com"))
)

val youiVersion = "0.13.17-SNAPSHOT"
val scalaXMLVersion = "2.0.0-M2"
val androidVersion = "4.1.1.4"
val gdxVersion = "1.9.2"
val vlcjVersion = "3.10.1"
val scalaTestVersion = "3.2.0-M4"

lazy val root = project.in(file("."))
  .aggregate(core, lwjgl, jglfw, ios, video, examples)
  .settings(
    publish := {},
    publishLocal := {}
  )

lazy val core = project.in(file("core"))
  .settings(
    name := "sgine-core",
    libraryDependencies ++= Seq(
      "io.youi" %% "youi-core" % youiVersion,
      "com.badlogicgames.gdx" % "gdx" % gdxVersion,
      "com.badlogicgames.gdx" % "gdx-freetype" % gdxVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
    )
  )

lazy val lwjgl = project.in(file("lwjgl"))
  .settings(
    name := "sgine-lwjgl",
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx-backend-lwjgl3" % gdxVersion
    )
  )
  .dependsOn(core)

lazy val jglfw = project.in(file("jglfw"))
  .settings(
    name := "sgine-jglfw",
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx-backend-jglfw" % gdxVersion
    )
  )
  .dependsOn(core)

lazy val android = project.in(file("android"))
  .settings(
    name := "sgine-android",
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx-backend-android" % gdxVersion,
      "com.google.android" % "android" % androidVersion % "provided"
    )
  )
  .dependsOn(core)

lazy val ios = project.in(file("ios"))
  .settings(
    name := "sgine-ios",
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx-backend-robovm" % gdxVersion
    )
  )
  .dependsOn(core)

lazy val tools = project.in(file("tools"))
  .settings(
    name := "sgine-tools",
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx-tools" % gdxVersion
    )
  )
  .dependsOn(core)

lazy val video = project.in(file("video"))
  .settings(
    name := "sgine-video",
    libraryDependencies ++= Seq(
      "uk.co.caprica" % "vlcj" % vlcjVersion
    )
  )
  .dependsOn(core, lwjgl)

lazy val examples = project.in(file("examples"))
  .settings(
    name := "sgine-examples",
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-desktop",
      "com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion classifier "natives-desktop",
      "org.scala-lang.modules" %% "scala-xml" % scalaXMLVersion
    )
  )
  .dependsOn(core, lwjgl, video)