package com.example.studenthub.di

import com.example.studenthub.data.repository.FacultyRepositoryImpl
import com.example.studenthub.data.repository.StudentRepositoryImpl
import com.example.studenthub.data.repository.UserRepositoryImpl
import com.example.studenthub.domain.repository.FacultyRepository
import com.example.studenthub.domain.repository.StudentRepository
import com.example.studenthub.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds UserRepository interface to UserRepositoryImpl using Hilt.
     * @param impl - UserRepositoryImpl instance
     * @return UserRepository interface
     * 🔹 This allows repository to be injected wherever needed
     */
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    /**
     * Binds FacultyRepository interface to FacultyRepositoryImpl.
     */
    @Binds
    @Singleton
    abstract fun bindFacultyRepository(impl: FacultyRepositoryImpl): FacultyRepository

    /**
     * Binds StudentRepository interface to StudentRepositoryImpl.
     */
    @Binds
    @Singleton
    abstract fun bindStudentRepository(impl: StudentRepositoryImpl): StudentRepository
}