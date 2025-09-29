package com.example.studenthub.data.repository

import com.example.studenthub.data.local.dao.StudentDao
import com.example.studenthub.data.mapper.toDomain
import com.example.studenthub.data.mapper.toEntity
import com.example.studenthub.domain.model.Student
import com.example.studenthub.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * StudentRepositoryImpl — implementation of the StudentRepository interface using Room (DAO).
 * This repository is used to avoid calling DAO directly from the domain layer.
 */
class StudentRepositoryImpl @Inject constructor(
    private val studentDao: StudentDao
) : StudentRepository {

    /**
     * Get all students.
     * DAO returns Flow<List<StudentEntity>>, which is mapped to the domain model.
     * Since Flow is returned, the UI will update in real-time.
     */
    override fun getAllSudents(): Flow<List<Student>> =
        studentDao.getAllStudents().map { list ->
            list.map { it.toDomain() } // Entity -> Domain
        }

    /**
     * Get students by faculty.
     * DAO getStudentsByFaculty(facultyId) returns Flow, which is mapped to the domain model.
     */
    override fun getStudentsByFaculty(facultyId: Long): Flow<List<Student>> =
        studentDao.getStudentsByFaculty(facultyId).map { list ->
            list.map { it.toDomain() } //Entity -> Domain
        }

    /**
     * Insert a new student.
     * Domain model is mapped to Entity and DAO insert method is called.
     */
    override suspend fun insertStudent(student: Student) {
        studentDao.insertStudent(student.toEntity())
    }

    /**
     * Update an existing student.
     * Domain model is mapped to Entity and DAO update method is called.
     */
    override suspend fun updateStudent(student: Student) {
        studentDao.updateStudent(student.toEntity())
    }

    /**
     * Delete a student by ID.
     */
    override suspend fun deleteStudent(studentId: Long) {
        studentDao.deleteStudent(studentId)
    }
}