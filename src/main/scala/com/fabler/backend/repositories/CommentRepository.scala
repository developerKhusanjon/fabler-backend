package com.fabler.backend.repositories

import cats.effect.IO
import com.fabler.backend.models.Comment
import doobie._
import doobie.implicits._
import doobie.hikari.HikariTransactor
import doobie.postgres.implicits._

class CommentRepository(xa: HikariTransactor[IO]) {

  def create(comment: Comment): IO[Comment] = {
    val query = sql"""
      INSERT INTO comments (id, content, user_id, fable_id, created_at, updated_at)
      VALUES (${comment.id}, ${comment.content}, ${comment.userId}, ${comment.fableId}, ${comment.createdAt}, ${comment.updatedAt})
    """.update.run

    query.transact(xa).map(_ => comment)
  }

  def findById(id: String): IO[Option[Comment]] = {
    sql"""
      SELECT id, content, user_id, fable_id, created_at, updated_at
      FROM comments
      WHERE id = $id
    """.query[Comment].option.transact(xa)
  }

  def findByFableId(fableId: String): IO[List[Comment]] = {
    sql"""
      SELECT id, content, user_id, fable_id, created_at, updated_at
      FROM comments
      WHERE fable_id = $fableId
      ORDER BY created_at ASC
    """.query[Comment].to[List].transact(xa)
  }

  def findByUserId(userId: String): IO[List[Comment]] = {
    sql"""
      SELECT id, content, user_id, fable_id, created_at, updated_at
      FROM comments
      WHERE user_id = $userId
      ORDER BY created_at DESC
    """.query[Comment].to[List].transact(xa)
  }

  def update(comment: Comment): IO[Comment] = {
    val query = sql"""
      UPDATE comments
      SET content = ${comment.content}, updated_at = ${comment.updatedAt}
      WHERE id = ${comment.id}
    """.update.run

    query.transact(xa).map(_ => comment)
  }

  def delete(id: String): IO[Boolean] = {
    sql"""
      DELETE FROM comments
      WHERE id = $id
    """.update.run.transact(xa).map(_ > 0)
  }

  def deleteByFableId(fableId: String): IO[Int] = {
    sql"""
      DELETE FROM comments
      WHERE fable_id = $fableId
    """.update.run.transact(xa)
  }
}