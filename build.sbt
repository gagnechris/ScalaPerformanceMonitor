name := "ScalaPerformanceMonitor"

version := "1.0"

scalaVersion := "2.10.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val aspectjVersion = "1.7.2"
  val scalatestVersion = "2.0.M7"
  Seq(
  "org.aspectj"             % "aspectjweaver"         % aspectjVersion,
  "org.aspectj"             % "aspectjrt"             % aspectjVersion,
  "commons-lang"            % "commons-lang"          % "2.6",
  "ch.qos.logback"          % "logback-classic"       % "1.0.13",
  "com.typesafe"           %% "scalalogging-slf4j"    % "1.0.1",
  "org.scalatest"          %% "scalatest"             % scalatestVersion % "test"
  )
}

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

javaOptions in run += "-javaagent:" + System.getProperty("user.home") + "/.ivy2/cache/org.aspectj/aspectjweaver/jars/aspectjweaver-1.7.2.jar"
