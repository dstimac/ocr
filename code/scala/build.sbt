organization := "hr.element.ocr"

name := "ocr"

version := "0.0.1"

scalaVersion := "2.9.1"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise")

unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(_ :: Nil)

unmanagedSourceDirectories in Test <<= (scalaSource in Test)(_ :: Nil)

resolvers := Seq(
  "Element Nexus"             at "http://maven.element.hr/nexus/content/groups/public/",
  "Element Releases"          at "http://maven.element.hr/nexus/content/repositories/releases/",
  "Element Snapshots"         at "http://maven.element.hr/nexus/content/repositories/snapshots/",
  "Element Private Releases"  at "http://maven.element.hr/nexus/content/repositories/releases-private/",
  "Element Private Snapshots" at "http://maven.element.hr/nexus/content/repositories/snapshots-private/"
)

externalResolvers <<= resolvers map { rs =>
  Resolver.withDefaultResolvers(rs, mavenCentral = false, scalaTools = false)
}

libraryDependencies ++= Seq(
    "hr.element.etb" %% "etb-util" % "0.2.16"
  , "com.github.scala-incubator.io" % "scala-io-core_2.9.1" % "0.4.1"
  , "com.github.scala-incubator.io" % "scala-io-file_2.9.1" % "0.4.1-seq"
  , "org.streum" %% "configrity-core" % "0.10.2"
)

publishTo <<= (version) ( v => Some(
  if (v endsWith "SNAPSHOT") {
    "Element Snapshots" at "http://maven.element.hr/nexus/content/repositories/snapshots/"
  }
  else {
    "Element Releases" at "http://maven.element.hr/nexus/content/repositories/releases/"
  }
))

credentials += Credentials(Path.userHome / ".publish" / "element.credentials")

publishArtifact in (Compile, packageDoc) := false
