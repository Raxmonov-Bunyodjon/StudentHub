package com.example.studenthub.ui.main.student.addstudent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studenthub.data.local.entity.FacultyEntity
import com.example.studenthub.data.local.entity.StudentEntity
import com.example.studenthub.data.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 🔹 AddStudentViewModel
 *
 * Handles the logic for adding (ADD) and editing (EDIT) students.
 * Manages faculty flow, selected faculty, and CRUD operations for students.
 */
@HiltViewModel
class AddStudentViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    // ---------------------------
    // 🔹 Flow of all faculties (for Spinner/Dialog)
    // ---------------------------
    val faculties: Flow<List<FacultyEntity>> = repository.getFaculties()

    // ---------------------------
    // 🔹 Store the selected faculty
    // ---------------------------
    private val _selectedFaculty = MutableStateFlow<FacultyEntity?>(null)
    val selectedFaculty: StateFlow<FacultyEntity?> = _selectedFaculty.asStateFlow()

    fun selectFaculty(faculty: FacultyEntity) {
        _selectedFaculty.value = faculty
    }

    // ---------------------------
    // 🔹 Add a new student
    // ---------------------------
    fun addStudent(student: StudentEntity) {
        viewModelScope.launch {
            repository.addStudent(student)
        }
    }

    // ---------------------------
    // 🔹 Update an existing student
    // ---------------------------
    fun updateStudent(student: StudentEntity) {
        viewModelScope.launch {
            repository.updateStudent(student)
        }
    }

    // ---------------------------
    // 🔹 Delete a student
    // ---------------------------
    fun deleteStudent(id: Long) {
        viewModelScope.launch {
            repository.deleteStudent(id)
        }
    }

    // ---------------------------
    // 🔹 Get student by ID (for EDIT mode)
    // ---------------------------
    suspend fun getStudentById(id: Long): StudentEntity? {
        return repository.getStudentById(id)
    }
}
