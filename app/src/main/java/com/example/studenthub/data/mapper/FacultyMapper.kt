package com.example.studenthub.data.mapper


import com.example.studenthub.data.local.entity.FacultyEntity
import com.example.studenthub.domain.model.Faculty


/**
 * Mapper functions — used to convert between Entity and Domain model.
 *
 * Data Layer (Room Entity) ↔ Domain Layer (Business logic)
 *
 * This way, the business logic of the app is not tightly coupled to Room.
 */

/**
 * FacultyEntity → Faculty (Domain Model)
 * Converts a Room Entity object into a Domain model.
 */
fun FacultyEntity.toDomain(): Faculty {
    return Faculty(id = id, name = name)
}


/**
 * Faculty (Domain Model) → FacultyEntity
 * Converts a Domain model object into a Room Entity format.
 */
fun Faculty.toEntity(): FacultyEntity {
    return FacultyEntity(id = id, name = name)
}