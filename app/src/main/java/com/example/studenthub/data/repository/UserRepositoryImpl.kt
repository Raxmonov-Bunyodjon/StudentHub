package com.example.studenthub.data.repository

import com.example.studenthub.data.local.dao.UserDao
import com.example.studenthub.data.local.entity.UserEntity
import com.example.studenthub.data.local.preferences.UserPreferences
import com.example.studenthub.domain.model.User
import com.example.studenthub.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * UserRepositoryImpl — Implementation of the UserRepository interface.
 * Manages user data using DAO (Room) and DataStore.
 */
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val preferences: UserPreferences,
) : UserRepository{

    // ============================
    // Username flow from DataStore (for session monitoring)
    // ============================
    override val userUsernameFlow: Flow<String?>
        get() = preferences.userUsernameFlow

    // ============================
    // Get list of users
    // ============================
    override fun getUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { list ->
            list.map { entity ->
                User(
                    id = entity.id,
                    firstName = entity.firstName,
                    lastName = entity.lastName,
                    username = entity.username,
                    password = entity.password
                )
            }
        }
    }

    // ============================
    // Login: Get a user by username and password
    // Returns null if no match is found
    // ============================
    override fun getUserByUsernameAndPassword(
        username: String,
        password: String
    ): Flow<User?> {
        return userDao.getUserByUsernameAndPassword(username, password).map { entity ->
            entity?.let {
                User(
                    id = it.id,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    username = it.username,
                    password = it.password
                )
            }
        }
    }

    // ============================
    // Signup: Check if a user exists by username
    // Returns null if no user is found
    // ============================
    override suspend fun getUserByUsername(username: String): User? {
        val entity = userDao.getUserByUsername(username).firstOrNull()
        return entity?.let {
            User(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                username = it.username,
                password = it.password,
                faculty = it.faculty,
                direction = it.direction,
                avatar = it.avatar

            )
        }
    }

    // ============================
    // Insert a new user into the database
    // ============================
    override suspend fun insertUser(user: User) {
        userDao.insertUser(
            UserEntity(
                id = user.id,
                firstName = user.firstName,
                lastName = user.lastName,
                username = user.username,
                password = user.password
            )
        )
    }

    // ============================
    // Delete user
    // ============================
    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user.id)
    }

    // ============================
    // Update user avatar
    // ============================
    override suspend fun updateUserAvatar(username: String, avatar: String) {
        userDao.updateAvatar(username, avatar)
    }

    // ============================
    // Save signed-in username into DataStore
    // ============================
    override suspend fun signInUser(username: String) {
        preferences.saveUsername(username)
    }


    // ============================
    // Logout: clear all stored session data from DataStore
    // ============================
    override suspend fun logout() {
        preferences.clearUser()
    }
}