package com.example.studenthub.domain.usecase

import com.example.studenthub.domain.model.User
import com.example.studenthub.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

/**
 * UseCase for logging in a user by verifying their username and password.
 *
 * This class handles the business logic for user login.
 * It interacts with the UserRepository to check credentials
 * and returns the User if found.
 *
 * @param repo UserRepository instance for data operations
 * */
class LoginUserUseCase(private val repo: UserRepository){

    /**
     * Checks the username and password and returns the matching user.
     *
     * @param username Login username
     * @param password Login password
     * @return User? Returns the User if credentials match, null otherwise
     * */
    suspend operator fun invoke(username: String, password: String): User?{
        val user = repo.getUserByUsernameAndPassword(username, password)
            .firstOrNull()
        return user
    }

}