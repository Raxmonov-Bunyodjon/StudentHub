package com.example.studenthub.ui.main.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studenthub.data.local.StudentWithFaculty
import com.example.studenthub.data.local.entity.StudentEntity
import com.example.studenthub.data.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 🔹 StudentViewModel
 *
 * Handles CRUD operations and search for students,
 * and manages the UI state via StateFlow.
 */
@HiltViewModel
class StudentViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    // 🔹 Internal state: list of students
    private val _students = MutableStateFlow<List<StudentWithFaculty>>(emptyList())
    val students: StateFlow<List<StudentWithFaculty>> get() = _students

    /**
     * 🔹 init block
     * Loads students when the fragment starts
     */
    init {
        loadStudents()
    }

    /**
     * 🔹 loadStudents
     * Fetches all students from the repository and updates the StateFlow
     */
    private fun loadStudents() {
        repository.getStudentsWithFaculty()
            .onEach { list ->
                _students.value = list
            }
            .launchIn(viewModelScope)
    }

    /**
     * 🔹 searchStudents
     * Performs search: if query is blank, returns all students,
     * otherwise filters the list based on the query
     */
    fun searchStudents(query: String) {
        if (query.isBlank()) {
            loadStudents()
        } else {
            repository.searchStudents(query)
                .onEach { list ->
                    _students.value = list
                }
                .launchIn(viewModelScope)
        }
    }

    /**
     * 🔹 addStudent
     * Adds a new student
     */
    fun addStudent(student: StudentEntity) {
        viewModelScope.launch { repository.addStudent(student) }
    }

    /**
     * 🔹 updateStudent
     * Updates an existing student
     */
    fun updateStudent(student: StudentEntity) {
        viewModelScope.launch { repository.updateStudent(student) }
    }

    /**
     * 🔹 deleteStudent
     * Deletes a student by ID
     */
    fun deleteStudent(id: Long) {
        viewModelScope.launch { repository.deleteStudent(id) }
    }

    /**
     * 🔹 getStudentById
     * Fetches a student by ID (for edit mode)
     */
    suspend fun getStudentById(id: Long): StudentWithFaculty? {
        return repository.getStudentWithFacultyById(id)
    }
}


