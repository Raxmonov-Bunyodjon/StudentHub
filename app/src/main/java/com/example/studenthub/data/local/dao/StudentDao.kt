package com.example.studenthub.data.local.dao

import androidx.room.*
import com.example.studenthub.data.local.StudentWithFaculty
import com.example.studenthub.data.local.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

/**
 * StudentDao — DAO interface for Room database.
 * This interface allows performing CRUD operations, searches, and joins
 * on the "students" table and its relation with faculties.
 */
@Dao
interface StudentDao {

    // === CRUD ===

    /**
     * Inserts a new student into the database.
     * If a student with the same ID already exists, it will be replaced.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentEntity)

    /**
     * Updates existing student data.
     * The student must have an ID — the update is performed based on this ID.
     */
    @Update
    suspend fun updateStudent(student: StudentEntity)

    /**
     * Retrieves a student by ID.
     * Returns null if not found.
     */
    @Query("SELECT * FROM students WHERE id = :id LIMIT 1")
    suspend fun getStudentById(id: Long): StudentEntity?

    /**
     * Deletes a student by ID.
     */
    @Query("DELETE FROM students WHERE id = :studentId")
    suspend fun deleteStudent(studentId: Long)

    /**
     * Retrieves all students.
     * Returns Flow<List<StudentEntity>> — automatically updates if the table changes.
     */
    @Query("SELECT * FROM students")
    fun getAllStudents(): Flow<List<StudentEntity>>

    /**
     * Retrieves students belonging to a specific faculty.
     */
    @Query("SELECT * FROM students WHERE facultyId = :facultyId")
    fun getStudentsByFaculty(facultyId: Long): Flow<List<StudentEntity>>

    // === Search ===

    /**
     * Searches students by first or last name.
     * Returns a list of StudentWithFaculty objects including faculty name.
     */
    @Query("""
    SELECT s.id, s.firstName, s.lastName, s.facultyId,
           f.name AS facultyName, s.direction, s.avatar
    FROM students s
    INNER JOIN faculties f ON s.facultyId = f.id
    WHERE s.firstName LIKE :query || '%'
       OR s.lastName LIKE :query || '%'
""")
    fun searchStudents(query: String): Flow<List<StudentWithFaculty>>

    // === JOIN query: StudentWithFaculty ===

    /**
     * Retrieves all students along with their faculty information.
     */
    @Query("""
        SELECT s.id, s.firstName, s.lastName, s.facultyId,
               f.name AS facultyName, s.direction, s.avatar
        FROM students s
        INNER JOIN faculties f ON s.facultyId = f.id
    """)
    fun getStudentsWithFaculty(): Flow<List<StudentWithFaculty>>

    /**
     * Retrieves a student by ID along with faculty information.
     * Returns null if not found.
     */
    @Query("""
        SELECT s.id, s.firstName, s.lastName, s.facultyId,
               f.name AS facultyName, s.direction, s.avatar
        FROM students s
        INNER JOIN faculties f ON s.facultyId = f.id
        WHERE s.id = :id
    """)
    suspend fun getStudentWithFacultyById(id: Long): StudentWithFaculty?
}
