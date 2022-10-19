name := "fotos"
 
version := "1.0" 
      
lazy val `fotos` = (project in file(".")).enablePlugins(PlayScala)

      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.13.8"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
      