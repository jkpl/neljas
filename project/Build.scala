import sbt._

object Build {
}

object Resolvers {
  val http4s = "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

  val all = List(http4s)
}

object Dependencies {
  object http4s {
    val version = "0.12.3"
    val dsl = "org.http4s" %% "http4s-dsl" % version
    val blazeServer = "org.http4s" %% "http4s-blaze-server" % version
//     val servlet = "org.http4s" %% "http4s-servlet" % version
//     val jetty = "org.http4s" %% "http4s-jetty" % version
//     val blazeClient = "org.http4s" %% "http4s-blaze-client" % version

    val deps = List(dsl, blazeServer)
  }

  val all = http4s.deps
}