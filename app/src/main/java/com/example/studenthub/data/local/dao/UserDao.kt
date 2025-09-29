package com.example.studenthub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.studenthub.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow


/**
 * UserDao — DAO interface for managing UserEntity with Room.
 * Provides CRUD operations and queries for the "users" table.
 */
@Dao
interface UserDao {

    /** Insert a new user. Fails if the username already exists (ABORT). */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    /** Delete a user by ID. */
    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUser(id: Long)

    /** Get a user by username and password (for login). */
    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    fun getUserByUsernameAndPassword(username: String, password: String): Flow<UserEntity?>

    /** Get a user by username (check if user exists). */
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    fun getUserByUsername(username: String): Flow<UserEntity?>

    /** Get all users as a Flow. */
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    /** Update user's avatar by username. */
    @Query("UPDATE users SET avatar = :avatar WHERE username = :username")
    suspend fun updateAvatar(username: String, avatar: String)
}


