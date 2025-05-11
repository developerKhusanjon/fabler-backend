package com.fabler.backend.models

case class Fable(
                  id: String,
                  title: String,
                  content: String,
                  userId: String,
                  createdAt: Long,
                  updatedAt: Long
                )

object Fable {
  def create(title: String, content: String, userId: String): Fable = {
    val now = System.currentTimeMillis()
    Fable(
      id = java.util.UUID.randomUUID().toString,
      title = title,
      content = content,
      userId = userId,
      createdAt = now,
      updatedAt = now
    )
  }
}