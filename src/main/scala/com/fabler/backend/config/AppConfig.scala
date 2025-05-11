package com.fabler.backend.config

case class AppConfig(
                      server: ServerConfig,
                      database: DatabaseConfig,
                      firebase: FirebaseConfig
                    )

case class ServerConfig(
                         host: String,
                         port: Int
                       )

case class DatabaseConfig(
                           url: String,
                           user: String,
                           password: String,
                           driver: String,
                           poolSize: Int
                         )