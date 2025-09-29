package com.example.studenthub.domain.model

/**
 * Represents a user entity in the system.
 *
 * @property id Unique identifier for the user.
 * @property firstName User's first name.
 * @property lastName User's last name.
 * @property username Login username used for authentication.
 * @property password Plain-text password (⚠️ should be encrypted before storage).
 *
 * ⚠️ Note: Avoid storing plain-text passwords in production. Use hashing (e.g., bcrypt).
 */
data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val password: String,
    val faculty: String? = null,
    val direction: String? = null,
    val avatar: String? = null
)