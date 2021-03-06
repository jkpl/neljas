import sbt._

object Build {
}

object Resolvers {
  val http4s = "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

  val all = List(http4s)
}

object Dependencies {
  val db = {
    val doobieVersion = "0.2.3"
    List(
      "org.tpolecat" %% "doobie-core" % doobieVersion,
      "org.tpolecat" %% "doobie-contrib-h2" % doobieVersion,
      "com.h2database" % "h2" % "1.4.191",
      "com.zaxxer" % "HikariCP" % "2.4.3"
    )
  }

  val http4s = {
    val version = "0.12.3"

    List(
      "org.http4s" %% "http4s-dsl" % version,
      "org.http4s" %% "http4s-twirl" % version,
      "org.http4s" %% "http4s-blaze-server" % version,
      "org.http4s" %% "http4s-argonaut" % version
    )
  }

  val logging = List(
    "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
    "ch.qos.logback" % "logback-classic" % "1.1.6"
  )

  val pdf = List("io.github.cloudify" %% "spdf" % "1.3.3")

  val email = List("org.apache.commons" % "commons-email" % "1.4")

  val conf = List("com.typesafe" % "config" % "1.3.0")

  val json = {
    val argonautVersion = "6.1"
    List("io.argonaut" %% "argonaut" % argonautVersion)
  }

  val all = List.concat(db, http4s, logging, pdf, email, conf, json)
}
