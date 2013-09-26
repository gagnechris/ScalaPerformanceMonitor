name := "ScalaPerformanceMonitor"

version := "1.0"

scalaVersion := "2.10.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  val sprayVersion = "1.2-M8"
  val akkaVersion = "2.2.0-RC1"
  val aspectjVersion = "1.7.2"
  val scalatestVersion = "2.0.M7"
  Seq(
  "io.spray"                % "spray-can"             % sprayVersion,
  "io.spray"                % "spray-routing"         % sprayVersion,
  "io.spray"                % "spray-testkit"         % sprayVersion,
  "io.spray"                % "spray-client"          % sprayVersion,
  "io.spray"               %%  "spray-json"           % "1.2.5",
  "com.typesafe.akka"      %% "akka-actor"            % akkaVersion,
  "com.typesafe.akka"      %% "akka-slf4j"            % akkaVersion,
  "com.typesafe.akka"      %% "akka-testkit"          % akkaVersion      % "test",
  "org.aspectj"             % "aspectjweaver"         % aspectjVersion,
  "org.aspectj"             % "aspectjrt"             % aspectjVersion,
  "ch.qos.logback"          % "logback-classic"       % "1.0.13",
  "com.typesafe"           %% "scalalogging-slf4j"    % "1.0.1",
  "org.scalatest"          %% "scalatest"             % scalatestVersion % "test"
  )
}

seq(Revolver.settings: _*)

javaOptions in run += "-javaagent:" + System.getProperty("user.home") + "/.ivy2/cache/org.aspectj/aspectjweaver/jars/aspectjweaver-1.7.2.jar"

exportJars := true

fork in run := true

connectInput in run := true