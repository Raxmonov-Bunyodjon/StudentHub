package com.example.studenthub.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studenthub.data.local.dao.FacultyDao
import com.example.studenthub.data.local.dao.StudentDao
import com.example.studenthub.data.local.dao.UserDao
import com.example.studenthub.data.local.entity.FacultyEntity
import com.example.studenthub.data.local.entity.StudentEntity
import com.example.studenthub.data.local.entity.UserEntity
import com.example.studenthub.ui.main.faculty.FacultyFragment



/**
 * Main Database class for the Room library.
 * It combines all Entities (tables) and DAOs (data access objects).
 */
@Database(
    entities = [
        UserEntity::class,      // User table
        StudentEntity::class,   // Students table
        FacultyEntity::class  // Faculties table
    ],
    version = 1,  // Database version (increase this when entities or columns are changed)
    exportSchema = false  // If true, Room exports schema into a folder (usually not needed for development)
)

abstract class AppDatabase: RoomDatabase() {

    /**
     * DAO for the Users table.
     * Contains methods for handling users (insert, delete, update, query).
     */
    abstract fun userDao(): UserDao

    /**
     * DAO for the Students table.
     * Handles operations related to students.
     */
    abstract fun studentDao(): StudentDao

    /**
     * DAO for the Faculties table.
     * Used to access and manage faculty data.
     */
    abstract fun facultyDao(): FacultyDao
}