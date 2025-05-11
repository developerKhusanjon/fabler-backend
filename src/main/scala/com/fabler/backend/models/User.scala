package com.fabler.backend.models

case class User(
                 id: String,
                 email: String,
                 name: String,
                 bio: Option[String] = None,
                 profilePicture: Option[String],
                 createdAt: Long,
                 updatedAt: Long
               )

object User {
  def create(id: String, email: String, name: String,  bio: Option[String] = None, profilePicture: Option[String] = None): User = {
    val now = System.currentTimeMillis()
    User(
      id = id,
      email = email,
      name = name,
      bio = None,
      profilePicture = profilePicture,
      createdAt = now,
      updatedAt = now
    )
  }
}

