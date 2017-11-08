name := """play-scala-starter-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb)

resolvers += Resolver.sonatypeRepo("snapshots")

LessKeys.compress in Assets := true

pipelineStages := Seq(digest, gzip)

includeFilter in (Assets, LessKeys.less) := "*.less"

routesGenerator := InjectedRoutesGenerator

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.196"

// Used for icons
libraryDependencies += "org.webjars" % "font-awesome" % "4.7.0"

// Accordingto Play bootstrap we need to specify the lib version
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.7-1"
libraryDependencies += "org.webjars" % "jquery" % "3.2.1"

libraryDependencies += "com.adrianhurt" %% "play-bootstrap" % "1.2-P26-B3"