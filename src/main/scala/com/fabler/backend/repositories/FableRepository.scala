package com.fabler.backend.repositories

import cats.effect.IO
import com.fabler.backend.models.Fable
import doobie._
import doobie.implicits._
import doobie.hikari.HikariTransactor
import doobie.postgres.implicits._

class FableRepository(xa: HikariTransactor[IO]) {

  def create(fable: Fable): IO[Fable] = {
    val query =
      sql"""
        INSERT INTO fables (id, title, content, user_id, created_at, updated_at)
        VALUES (${fable.id}, ${fable.title}, ${fable.content}, ${fable.userId}, ${fable.createdAt}, ${fable.updatedAt})
      """.update.run

    query.transact(xa).map(_ => fable)
  }

  def findById(id: String): IO[Option[Fable]] = {
    sql"""
        SELECT id, title, content, user_id, created_at, updated_at
        FROM fables
        WHERE id = $id
      """.query[Fable].option.transact(xa)
  }

  def findByUserId(userId: String): IO[List[Fable]] = {
    sql"""
        SELECT id, title, content, user_id, created_at, updated_at
        FROM fables
        WHERE user_id = $userId
        ORDER BY created_at DESC
      """.query[Fable].to[List].transact(xa)
  }

  def update(fable: Fable): IO[Fable] = {
    val query =
      sql"""
        UPDATE fables
        SET title = ${fable.title}, content = ${fable.content}, updated_at = ${fable.updatedAt}
        WHERE id = ${fable.id}
      """.update.run

    query.transact(xa).map(_ => fable)
  }

  def delete(id: String): IO[Boolean] = {
    sql"""
        DELETE FROM fables
        WHERE id = $id
      """.update.run.transact(xa).map(_ > 0)
  }

  def listAll(limit: Int = 100, offset: Int = 0): IO[List[Fable]] = {
    sql"""
        SELECT id, title, content, user_id, created_at, updated_at
        FROM fables
        ORDER BY created_at DESC
        LIMIT $limit OFFSET $offset
      """.query[Fable].to[List].transact(xa)
  }

  def search(query: String): IO[List[Fable]] = {
    val searchTerm = s"%$query%"
    sql"""
        SELECT id, title, content, user_id, created_at, updated_at
        FROM fables
        WHERE title ILIKE $searchTerm OR content ILIKE $searchTerm
        ORDER BY created_at DESC
      """.query[Fable].to[List].transact(xa)
  }
}
