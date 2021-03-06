package neljas.db

import java.sql.Connection

import com.typesafe.scalalogging.LazyLogging
import com.zaxxer.hikari.{HikariDataSource, HikariConfig}
import doobie.imports.{Capture, Transactor}
import neljas.conf.Conf

import scalaz.concurrent.Task
import scalaz.{Nondeterminism, Catchable, Monad}

final class Database(conf: Conf.Database) extends LazyLogging {
  val hikariConfig = {
    val config = new HikariConfig()
    config.setDriverClassName(conf.driver)
    config.setJdbcUrl(conf.url)
    config.setUsername(conf.username)
    config.setPassword(conf.password)
    config
  }

  val dataSource = new HikariDataSource(hikariConfig)

  val xa: Transactor[Task] = new HikariTransactor[Task](dataSource)

  val repos = new Repositories(xa)

  def close(): Unit = {
    logger.info("Closing database data source")
    dataSource.close()
  }

  def init(): Unit = {
    logger.info("Initializing database schemas")
    repos.initSchemas.run
  }
}

final class Repositories(xa: Transactor[Task]) {
  val message = new MessageRepository(xa)

  val all: List[Repository] = List(message)

  def initSchemas: Task[Unit] =
    Nondeterminism[Task].reduceUnordered[Unit, Unit](all.map(_.initSchema))
}

trait Repository {
  def initSchema: Task[Unit]
}

protected final class HikariTransactor[M[_]: Monad : Catchable : Capture](ds: HikariDataSource) extends Transactor {
  override protected def connect: M[Connection] = Capture[M].apply(ds.getConnection)
}
