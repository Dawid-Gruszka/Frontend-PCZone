package com.david.pczone.Models

data class UserBase(
    val username: String,
    val email: String,
    val address: String
)

data class UserCreate(
    val username: String,
    val email: String,
    val password: String,
    val address: String

)

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val address: String
)

data class Token(
    val access_token: String,
    val token_type: String
)

data class TokenData(
    val username: String? = null
)
