package com.example.studenthub.ui.main.faculty.addfaculty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studenthub.domain.model.Faculty
import com.example.studenthub.domain.repository.FacultyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 🔹 AddFacultyViewModel
 *   - Handles business logic for adding and editing faculties
 *   - Interacts with the database through the repository
 */
@HiltViewModel
class AddFacultyViewModel @Inject constructor(
    private val repository: FacultyRepository
) : ViewModel() {

    /**
     * 🔹 Add a new faculty
     * @param name Faculty name
     * id = 0, Room will automatically assign a new ID
     */
    fun addFaculty(name: String) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.insertFaculty(Faculty(0, name))
        }
    }

    /**
     * 🔹 Update an existing faculty
     * @param id Faculty ID
     * @param name New name
     */
    fun updateFaculty(id: Long, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFaculty(Faculty(id, name))
        }
    }

    /**
     * 🔹 Get a faculty by ID
     * Useful for Edit mode
     * @param id Faculty ID
     * @return Faculty or null if not found
     */
    suspend fun getFacultyById(id: Long): Faculty? {
        return repository.getFacultyById(id)
    }
}