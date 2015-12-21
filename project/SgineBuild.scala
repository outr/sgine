import sbt.Keys._
import sbt._

object SgineBuild extends Build {
  import Dependencies._

  lazy val root = Project(id = "root", base = file(".")) aggregate(core, lwjgl, jglfw, android, ios, tools)
  lazy val core = project("core").withDependencies(gdx.core, metastack.rx)
  lazy val lwjgl = project("lwjgl").dependsOn(core).withDependencies(gdx.lwjgl)
  lazy val jglfw = project("jglfw").dependsOn(core).withDependencies(gdx.jglfw)
  lazy val android = project("android").dependsOn(core).withDependencies(google.android, gdx.android)
  lazy val ios = project("ios").dependsOn(core).withDependencies(gdx.ios)
  lazy val tools = project("tools").dependsOn(lwjgl).withDependencies(gdx.tools)
  lazy val examples = project("examples").dependsOn(jglfw, lwjgl).withDependencies(gdx.desktopNatives, scalaXML)

  private def project(projectName: String) = Project(id = projectName, base = file(projectName)).settings(
    name := s"${Details.name}-$projectName",
    version := Details.version,
    organization := Details.organization,
    scalaVersion := Details.scalaVersion,
    sbtVersion := Details.sbtVersion,
    fork := true,
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    javaOptions += "-verbose:gc",
    resolvers ++= Seq(
      "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
      "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"
    ),
    publishTo <<= version {
      (v: String) =>
        val nexus = "https://oss.sonatype.org/"
        if (v.trim.endsWith("SNAPSHOT"))
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    publishArtifact in Test := false,
    pomExtra := <url>${Details.url}</url>
      <licenses>
        <license>
          <name>${Details.licenseType}</name>
          <url>${Details.licenseURL}</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <developerConnection>scm:${Details.repoURL}</developerConnection>
        <connection>scm:${Details.repoURL}</connection>
        <url>${Details.projectURL}</url>
      </scm>
      <developers>
        <developer>
          <id>${Details.developerId}</id>
          <name>${Details.developerName}</name>
          <url>${Details.developerURL}</url>
        </developer>
      </developers>
  )

  implicit class EnhancedProject(project: Project) {
    def withDependencies(modules: ModuleID*) = project.settings(libraryDependencies ++= modules)
  }
}

object Details {
  val organization = "org.sgine"
  val name = "sgine"
  val version = "2.0.0-SNAPSHOT"
  val url = "http://sgine.org"
  val licenseType = "Apache 2.0"
  val licenseURL = "http://www.apache.org/licenses/LICENSE-2.0"
  val projectURL = "https://gitlab.com/outr/sgine"
  val repoURL = "https://gitlab.com/outr/sgine.git"
  val developerId = "darkfrog"
  val developerName = "Matt Hicks"
  val developerURL = "http://matthicks.com"

  val sbtVersion = "0.13.9"
  val scalaVersion = "2.11.7"
}

object Dependencies {
  val gdxVersion = "1.7.2"

  val scalaXML = "org.scala-lang.modules" %% "scala-xml" % "1.0.5"

  object google {
    val android = "com.google.android" % "android" % "4.1.1.4" % "provided"
  }

  object metastack {
    val rx = "pl.metastack" %%  "metarx" % "0.1.4"
  }

  object gdx {
    val core = "com.badlogicgames.gdx" % "gdx" % gdxVersion
    val lwjgl = "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % gdxVersion
    val jglfw = "com.badlogicgames.gdx" % "gdx-backend-jglfw" % gdxVersion
    val android = "com.badlogicgames.gdx" % "gdx-backend-android" % gdxVersion
    val ios = "com.badlogicgames.gdx" % "gdx-backend-robovm" % gdxVersion
    val tools = "com.badlogicgames.gdx" % "gdx-tools" % gdxVersion

    val desktopNatives = "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-desktop"
  }
}
