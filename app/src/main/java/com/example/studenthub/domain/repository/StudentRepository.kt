package com.example.studenthub.domain.repository

import com.example.studenthub.domain.model.Student
import kotlinx.coroutines.flow.Flow


/**
 * StudentRepository — domain layer repository interface.
 *
 * Purpose:
 * - Provides abstraction for working with student data.
 * - Decouples the UI and business logic layers from the data sources
 *   (e.g., Room database, REST API).
 *
 * With this interface we can:
 * - fetch all students
 * - fetch students filtered by faculty
 * - insert, update, and delete students
 */
interface StudentRepository {

    /**
     * Fetch all students.
     * Returns Flow<List<Student>> — as a reactive data stream.
     * Whenever the table changes, the UI will be updated automatically.
     */
    fun getAllSudents(): Flow<List<Student>>

    /**
     * Fetch students by faculty.
     *
     * @param facultyId — faculty identifier
     * @return Flow<List<Student>>
     */
    fun getStudentsByFaculty(facultyId: Long): Flow<List<Student>>

    /**
     * Insert a new student.
     *
     * @param student — Student domain model to be added
     */
    suspend fun insertStudent(student: Student)

    /**
     * Update a student's information.
     *
     * @param student — Student domain model to be updated
     */
    suspend fun updateStudent(student: Student)

    /**
     * Delete a student.
     *
     * @param student — the ID (Long) of the student to be deleted
     */
    suspend fun deleteStudent(student: Long)
}