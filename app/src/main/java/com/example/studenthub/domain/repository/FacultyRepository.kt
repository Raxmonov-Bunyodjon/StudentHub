package com.example.studenthub.domain.repository

import com.example.studenthub.domain.model.Faculty
import kotlinx.coroutines.flow.Flow


/**
 * FacultyRepository — domain layer repository interface.
 *
 * Purpose:
 * - Provides abstraction for working with faculties.
 * - Decouples the UI and business logic layers from the data sources
 *   (e.g., Room database, REST API).
 *
 * With this interface we can:
 * - fetch faculty data
 * - insert
 * - delete
 * - update
 */
interface FacultyRepository {

    /**
     * Fetch all faculties.
     * Returns Flow<List<Faculty>> — a reactive data stream.
     * Whenever the table changes, the UI will be updated automatically.
     */
    fun getAllFaculties(): Flow<List<Faculty>>

    /**
     * Fetch a single faculty by ID.
     * Returns null if not found.
     * suspend — indicates this function should be called within a coroutine.
     */
    suspend fun getFacultyById(id: Long): Faculty?

    /**
     * Insert a new faculty.
     * suspend — indicates this function should be called within a coroutine.
     *
     * @param faculty — Faculty domain model to be added
     */
    suspend fun insertFaculty(faculty: Faculty)

    /**
     * Delete a faculty by ID.
     *
     * @param id — identifier of the faculty to be deleted
     */
    suspend fun deleteFaculty(id: Long)

    /**
     * Update existing faculty data.
     *
     * @param faculty — Faculty domain model to be updated (based on id)
     */
    suspend fun updateFaculty(faculty: Faculty)
}