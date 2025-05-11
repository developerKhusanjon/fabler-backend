package com.fabler.backend.models

case class Comment(
                    id: String,
                    content: String,
                    userId: String,
                    fableId: String,
                    createdAt: Long,
                    updatedAt: Long
                  )

object Comment {
  def create(content: String, userId: String, fableId: String): Comment = {
    val now = System.currentTimeMillis()
    Comment(
      id = java.util.UUID.randomUUID().toString,
      content = content,
      userId = userId,
      fableId = fableId,
      createdAt = now,
      updatedAt = now
    )
  }
}
