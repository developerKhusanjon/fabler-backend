package com.fabler.backend.repositories

import cats.effect.IO
import com.fabler.backend.models.User
import doobie._
import doobie.implicits._
import doobie.hikari.HikariTransactor
import doobie.postgres.implicits._

class UserRepository(xa: HikariTransactor[IO]) {

  def create(user: User): IO[User] = {
    val query = sql"""
      INSERT INTO users (id, email, name, profile_picture, created_at, updated_at)
      VALUES (${user.id}, ${user.email}, ${user.name}, ${user.profilePicture}, ${user.createdAt}, ${user.updatedAt})
    """.update.run

    query.transact(xa).map(_ => user)
  }

  def findById(id: String): IO[Option[User]] = {
    sql"""
      SELECT id, email, name, profile_picture, created_at, updated_at
      FROM users
      WHERE id = $id
    """.query[User].option.transact(xa)
  }

  def findByEmail(email: String): IO[Option[User]] = {
    sql"""
      SELECT id, email, name, profile_picture, created_at, updated_at
      FROM users
      WHERE email = $email
    """.query[User].option.transact(xa)
  }

  def update(user: User): IO[User] = {
    val query = sql"""
      UPDATE users
      SET email = ${user.email}, name = ${user.name}, profile_picture = ${user.profilePicture}, updated_at = ${user.updatedAt}
      WHERE id = ${user.id}
    """.update.run

    query.transact(xa).map(_ => user)
  }

  def delete(id: String): IO[Boolean] = {
    sql"""
      DELETE FROM users
      WHERE id = $id
    """.update.run.transact(xa).map(_ > 0)
  }

  def listAll: IO[List[User]] = {
    sql"""
      SELECT id, email, name, profile_picture, created_at, updated_at
      FROM users
      ORDER BY created_at DESC
    """.query[User].to[List].transact(xa)
  }
}

