package com.example.studenthub.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studenthub.domain.model.User
import com.example.studenthub.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * 🔹 ProfileViewModel
 *
 * ViewModel for managing the user profile.
 * Observes and updates the logged-in user and avatar through UserRepository.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    /**
     * 🔹 userFlow
     *
     * Observes the currently logged-in user.
     * Exposed as a StateFlow for lifecycle-safe collection.
     *
     * userUsernameFlow (repository) -> username -> fetch full User data
     */
    val userFlow: StateFlow<User?> = userRepository.userUsernameFlow
        .map { username ->
            if (username.isNullOrEmpty()) null
            else userRepository.getUserByUsername(username)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,  // Flow runs only when collected
            initialValue = null
        )

    /**
     * 🔹 saveAvatar
     *
     * Saves the selected avatar URI to the user profile.
     * Works with the currently observed user from Flow.
     */
    fun saveAvatar(avatarUri: String) {
        viewModelScope.launch {
            val currentUser = userFlow.value
            currentUser?.let { user ->
                // Update avatar in repository
                userRepository.updateUserAvatar(user.username, avatarUri)
            }
        }
    }
}