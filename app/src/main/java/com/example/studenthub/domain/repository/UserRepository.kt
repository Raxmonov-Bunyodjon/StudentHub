package com.example.studenthub.domain.repository

import com.example.studenthub.domain.model.User
import kotlinx.coroutines.flow.Flow


/**
 * UserRepository — domain layer repository interface for user-related operations.
 *
 * Purpose:
 * - Provides abstraction for working with user data.
 * - Decouples UI and business logic layers from the underlying data sources
 *   (e.g., Room Database, SharedPreferences, or remote API).
 */
interface UserRepository {

    /**
     * Stream of the currently signed-in user's username.
     * Useful for monitoring login/session state.
     */
    val userUsernameFlow: Flow<String?>


    /**
     * Retrieve all users.
     * Returns Flow<List<User>> — as a reactive data stream.
     */
    fun getUsers(): Flow<List<User>>

    // Login-related methods
    /** Get user by username and password for login verification */
    fun getUserByUsernameAndPassword(
        username: String,
        password: String
    ): Flow<User?>

    /**
     * Update a user's avatar.
     *
     * @param username — identifier of the user (username is used as key)
     * @param avatar — new avatar URL or file name
     */
    suspend fun updateUserAvatar(username: String, avatar: String)

    //Signup-related methods
    /** Check if a username already exists in DB */
    suspend fun getUserByUsername(username: String): User?

    /** Insert a new user into the database */
    suspend fun insertUser(user: User)

    /**
     * Delete a user.
     *
     * @param user — User domain model to be deleted
     */
    suspend fun deleteUser(user: User)

    /** Save login state in preferences / session */
    suspend fun signInUser(username: String)

    /** Clear session / logout user */
    suspend fun logout()
}