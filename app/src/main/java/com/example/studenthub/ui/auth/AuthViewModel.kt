package com.example.studenthub.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studenthub.domain.model.User
import com.example.studenthub.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * AuthViewModel — ViewModel responsible for handling user authentication logic.
 *
 * Responsibilities:
 * - Manages login and signup processes
 * - Exposes authentication state via [StateFlow] for UI observation
 * - Tracks the currently signed-in user via [Flow]
 *
 * @property userRepository Repository interface for user-related data operations
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    /**
     * Internal state of the authentication process.
     * Backed by [MutableStateFlow] and exposed as read-only [StateFlow] to the UI.
     */
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState


    /**
     * Flow of the currently signed-in user's username.
     * Can be observed by the UI to react to session changes.
     */
    val currentUserFlow: Flow<String?> = userRepository.userUsernameFlow


    // ========================
    // ✅ LOGIN
    // ========================

    /**
     * Attempts to log in the user with the provided credentials.
     *
     * @param username The entered username
     * @param password The entered password
     *
     * Updates [authState] with:
     * - [AuthState.Success] if login is successful
     * - [AuthState.Error] if credentials are invalid
     */
    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUserByUsernameAndPassword(username, password).collect { user ->
                if (user == null) {
                    _authState.emit(AuthState.Error("Invalid username or password!"))
                } else {
                    userRepository.signInUser(username)
                    _authState.emit(
                        AuthState.Success("Welcome ${user.firstName} ${user.lastName}")
                    )
                }
            }
        }
    }


    // ========================
    // ✅ SIGNUP
    // ========================

    /**
     * Registers a new user in the system.
     *
     * @param firstName New user's first name
     * @param lastName New user's last name
     * @param username Desired username (must be unique)
     * @param password Chosen password
     *
     * Updates [authState] with:
     * - [AuthState.Success] if registration is successful
     * - [AuthState.Error] if the username already exists
     */
    fun signup(firstName: String, lastName: String, username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingUser = userRepository.getUserByUsername(username)
            if (existingUser != null) {
                _authState.value = AuthState.Error("User already exists!")
                return@launch
            }

            val newUser = User(
                id = 0, // Room DB auto-generates ID
                firstName = firstName,
                lastName = lastName,
                username = username,
                password = password
            )

            userRepository.insertUser(newUser)
            userRepository.signInUser(username)

            _authState.value = AuthState.Success("Registration successful!")
        }
    }
}