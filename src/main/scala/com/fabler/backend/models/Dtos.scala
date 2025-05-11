package com.fabler.backend.models
// Used for login requests
case class LoginRequest(
                         email: String,
                         password: String
                       )

// Used for sign up requests
case class RegisterRequest(
                            email: String,
                            password: String,
                            name: String,
                            profilePicture: Option[String] = None
                          )

// Used for login responses
case class AuthResponse(
                         token: String,
                         user: User
                       )

// Used for token verification
case class TokenVerificationRequest(
                                     token: String
                                   )

// Model for storing user claims from the token
case class UserClaims(
                       userId: String,
                       email: String,
                       name: String
                     )
