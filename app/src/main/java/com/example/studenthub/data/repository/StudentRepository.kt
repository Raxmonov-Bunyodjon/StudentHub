package com.example.studenthub.data.repository

import com.example.studenthub.data.local.StudentWithFaculty
import com.example.studenthub.data.local.dao.FacultyDao
import com.example.studenthub.data.local.dao.StudentDao
import com.example.studenthub.data.local.dao.UserDao
import com.example.studenthub.data.local.entity.FacultyEntity
import com.example.studenthub.data.local.entity.StudentEntity
import com.example.studenthub.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


/**
 * StudentRepository — a single repository for managing all local data (Faculty, Student, User).
 * This repository allows working with DAOs indirectly from the domain or ViewModel layer,
 * without accessing them directly.
 */

@Singleton
class StudentRepository @Inject constructor(
    private val facultyDao: FacultyDao,
    private val studentDao: StudentDao,
    private val userDao: UserDao
){

    // ============================
    // Faculties (FacultyEntity)
    // ============================

    /**
     * Insert a new faculty.
     */
    suspend fun addFaculty(faculty: FacultyEntity) = facultyDao.insertFaculty(faculty)

    /**
     * Get all faculties (with real-time updates via Flow).
     */
    fun getFaculties(): Flow<List<FacultyEntity>> = facultyDao.getAllFaculties()

    /**
     * Delete a faculty by ID.
     */
    suspend fun deleteFaculty(id: Long) = facultyDao.deleteFaculty(id)




    // ============================
    // Students (with StudentEntity)
    // ============================



    /**
     * Insert a new student.
     */
    suspend fun addStudent(student: StudentEntity) = studentDao.insertStudent(student)

    /**
     * Get all students (as Entity).
     */
    fun getStudents(): Flow<List<StudentEntity>> = studentDao.getAllStudents()

    /**
     * Search students by first name or last name (using JOIN query).
     */
    fun searchStudents(query: String): Flow<List<StudentWithFaculty>> = studentDao.searchStudents(query)

    /**
     * Delete a student by ID.
     */
    suspend fun deleteStudent(id: Long) = studentDao.deleteStudent(id)

    /**
     * Update student data.
     */
    suspend fun updateStudent(student: StudentEntity) = studentDao.updateStudent(student)


    /**
     * Get a student by ID (as Entity).
     */
    suspend fun getStudentById(id: Long): StudentEntity? = studentDao.getStudentById(id)

    // ============================
    // Students (with StudentWithFaculty, JOIN query)
    // ============================

    /**
     * Get all students along with their faculty names.
     */
    fun getStudentWithFaculty(): Flow<List<StudentWithFaculty>> = studentDao.getStudentsWithFaculty()

    /**
     * Get a single student by ID (as JOIN result).
     */
    suspend fun getStudentWithFacultyById(id: Long): StudentWithFaculty? =
        studentDao.getStudentWithFacultyById(id)

    // ============================
    // Users (UserEntity)
    // ============================

    /**
     * Register a new user.
     */
    suspend fun registerUser(user: UserEntity) = userDao.insertUser(user)

    fun getStudentsWithFaculty(): Flow<List<StudentWithFaculty>> = studentDao.getStudentsWithFaculty()

    /**
     * Get a user by username and password.
     */
    fun getUserByUsernameAndPassword(username: String, password: String): Flow<UserEntity?> =
        userDao.getUserByUsernameAndPassword(username,password)

    /**
     * Update user avatar.
     */
    suspend fun updateUserAvatar(username: String, avatar: String) =
        userDao.updateAvatar(username,avatar)


    /**
     * Delete a user by ID.
     */
    suspend fun deleteUser(id: Int) = userDao.deleteUser(id.toLong())






}