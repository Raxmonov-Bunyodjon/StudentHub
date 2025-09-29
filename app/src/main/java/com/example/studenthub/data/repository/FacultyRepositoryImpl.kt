package com.example.studenthub.data.repository

import com.example.studenthub.data.local.dao.FacultyDao
import com.example.studenthub.data.local.entity.FacultyEntity
import com.example.studenthub.domain.model.Faculty
import com.example.studenthub.domain.repository.FacultyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


/**
 * FacultyRepositoryImpl — implementation of the FacultyRepository interface using Room (DAO).
 * This repository is used to avoid calling DAO directly from the domain layer.
 */
@Singleton
class FacultyRepositoryImpl @Inject constructor(
    private val facultyDao: FacultyDao
) : FacultyRepository {
    /**
     * Get all faculties.
     * DAO returns Flow<List<FacultyEntity>>, which is mapped to the domain model.
     * Since Flow is returned, the UI will update in real-time.
     */
    override fun getAllFaculties(): Flow<List<Faculty>> {
        return facultyDao.getAllFaculties().map { entities ->
            entities.map { Faculty(it.id, it.name) }
        }
    }

    /**
     * Get a faculty by its ID.
     * Uses Flow.first() to get a single value from DAO without collecting continuously.
     */
    override suspend fun getFacultyById(id: Long): Faculty? {
        val entities = facultyDao.getAllFaculties().first() // get the value once without collecting Flow
        return entities.find { it.id == id}?.let { Faculty(it.id, it.name) }
    }

    /**
     * Insert a new faculty.
     * If id = 0, Room will automatically generate and increment the ID.
     */
    override suspend fun insertFaculty(faculty: Faculty) {
        facultyDao.insertFaculty(FacultyEntity(id = 0, name = faculty.name))
    }

    /**
     * Delete a faculty by its ID.
     */
    override suspend fun deleteFaculty(id: Long) {
        facultyDao.deleteFaculty(id)
    }


    /**
     * Update an existing faculty.
     * Maps the domain model to Entity and calls DAO’s update method.
     */
    override suspend fun updateFaculty(faculty: Faculty) {
        facultyDao.updateFaculty(FacultyEntity(id = faculty.id, name = faculty.name))
    }
}