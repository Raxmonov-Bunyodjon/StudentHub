package com.example.studenthub.ui.auth

/**
 * AuthState — a sealed class that represents different states of the
 * authentication (login/signup) process.
 *
 * By using a sealed class, we can define a fixed set of possible states,
 * and the UI/ViewModel can react accordingly.
 */

sealed class AuthState {
    /**
     * Idle — the authentication process has not started or is in a waiting state.
     * Example: the user is on the login screen but hasn’t performed any action yet.
     */
    object Idle : AuthState()

    /**
     * Success — triggered when the authentication process completes successfully.
     * @param message — message to be shown to the user (e.g., "Login successful").
     */
    data class Success(val message: String) : AuthState()

    /**
     * Error — triggered when an error occurs during the authentication process.
     * @param message — error message to be displayed (e.g., "Invalid username or password").
     */
    data class Error(val message: String) : AuthState()
}