package com.fabler.backend.config

import cats.effect.{IO, Resource}
import doobie.hikari.HikariTransactor

object Database {
  def transactor(config: DatabaseConfig): Resource[IO, HikariTransactor[IO]] = {
    for {
      // Create a connection pool
      connectEC <- Resource.eval(IO.executionContext)

      // Create a transactor EC for executing JDBC operations
//      transactEC <- Resource.eval(IO.executionContext)

      // Create the transactor
      transactor <- HikariTransactor.newHikariTransactor[IO](
        config.driver,
        config.url,
        config.user,
        config.password,
        connectEC
      )

      // Configure the connection pool
      _ <- Resource.eval(transactor.configure { dataSource =>
        IO {
          dataSource.setMaximumPoolSize(config.poolSize)
          dataSource.setMinimumIdle(config.poolSize / 2)
          dataSource.setIdleTimeout(30000)
          dataSource.setMaxLifetime(2000000)
          dataSource.setConnectionTimeout(30000)
        }
      })
    } yield transactor
  }
}
