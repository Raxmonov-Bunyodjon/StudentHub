package com.example.studenthub.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studenthub.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository // UserRepository is injected
) : ViewModel() {

    /**
     * Logout function
     * 🔹 Logs the user out
     * 🔹 Clears username/token from SharedPreferences or local storage
     */
    fun logout() {
        // Launch coroutine with IO dispatcher
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.logout() // Clears username, token, and sets login status to false
        }
    }
}