package com.example.studenthub.data.local.dao

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.studenthub.data.local.entity.FacultyEntity

/**
 * FacultyDao — DAO interface for Room database.
 * Provides CRUD operations for the "faculties" table.
 */
@Dao
interface FacultyDao {

    /**
     * Inserts a new faculty into the database.
     * suspend — indicates this function runs inside a coroutine.
     */
    @Insert
    suspend fun insertFaculty(faculty: FacultyEntity)

    /**
     * Retrieves all faculties.
     * Returns Flow<List<FacultyEntity>> — data comes as a stream,
     * automatically updating if the table changes.
     */
    @Query("SELECT * FROM faculties ORDER BY id DESC")
    fun getAllFaculties(): Flow<List<FacultyEntity>>

    /**
     * Deletes a faculty by ID.
     * @param facultyId — ID of the faculty to delete.
     */
    @Query("DELETE FROM faculties WHERE id = :facultyId")
    suspend fun deleteFaculty(facultyId: Long)

    /**
     * Updates an existing faculty.
     * The faculty object must have an ID — the update is performed based on this ID.
     */
    @Update
    suspend fun updateFaculty(faculty: FacultyEntity)

    /**
     * Retrieves a single faculty by ID.
     * Returns null if not found.
     */
    @Query("SELECT * FROM faculties WHERE id = :id LIMIT 1")
    suspend fun getFacultyById(id: Long): FacultyEntity?
}
