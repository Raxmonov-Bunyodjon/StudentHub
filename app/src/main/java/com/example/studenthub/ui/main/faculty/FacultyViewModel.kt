package com.example.studenthub.ui.main.faculty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studenthub.domain.model.Faculty
import com.example.studenthub.domain.repository.FacultyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 🔹 FacultyViewModel
 *   - Handles faculty operations: load, search, add, update, delete
 *   - Connected to the UI via StateFlow
 */
@HiltViewModel
class FacultyViewModel @Inject constructor(
    private val repository: FacultyRepository
) : ViewModel() {

    // 🔹 Holds all faculties
    private val _allFaculties = MutableStateFlow<List<Faculty>>(emptyList())

    // 🔹 UI-facing filtered or full list of faculties
    private val _faculties = MutableStateFlow<List<Faculty>>(emptyList())
    val faculties: StateFlow<List<Faculty>> get() = _faculties

    init {
        loadFaculties() // 🔹 Initial load
    }

    /**
     * 🔹 Load all faculties from repository
     * and update _allFaculties and _faculties flows
     */
    private fun loadFaculties() {
        viewModelScope.launch {
            repository.getAllFaculties().collect { list ->
                _allFaculties.value = list
                _faculties.value = list // initially show all faculties
            }
        }
    }

    /**
     * 🔹 Search function
     *   - If query is empty, show all faculties
     *   - Otherwise, filter faculties matching the query
     */
    fun searchFaculties(query: String) {
        val filtered = if (query.isEmpty()) {
            _allFaculties.value
        } else {
            _allFaculties.value.filter { it.name.contains(query, ignoreCase = true) }
        }
        _faculties.value = filtered
    }

    /**
     * 🔹 Add a new faculty
     */
    fun addFaculty(name: String) {
        viewModelScope.launch {
            repository.insertFaculty(Faculty(0, name))
        }
    }

    /**
     * 🔹 Update an existing faculty
     */
    fun updateFaculty(faculty: Faculty) {
        viewModelScope.launch {
            repository.updateFaculty(faculty)
        }
    }

    /**
     * 🔹 Delete a faculty by its ID
     */
    fun deleteFaculty(id: Long) {
        viewModelScope.launch {
            repository.deleteFaculty(id)
        }
    }

    /**
     * 🔹 Get a faculty by ID (for example, for edit)
     */
    suspend fun getFacultyById(id: Long): Faculty? {
        return repository.getFacultyById(id)
    }
}
