package com.example.studenthub.data.mapper

import com.example.studenthub.data.local.StudentWithFaculty
import com.example.studenthub.data.local.entity.StudentEntity
import com.example.studenthub.domain.model.Student
import com.example.studenthub.ui.main.student.SimpleStudent

/**
 * Mapper functions — Converts between Student-related Entity, Domain, and UI models.
 * This reduces coupling between layers.
 */

/**
 * StudentEntity -> domain.Student
 * Converts DB Entity to domain model.
 * facultyName is null because StudentEntity does not contain it.
 */
fun StudentEntity.toDomain(): Student = Student(
    id = id,
    firstName = firstName,
    lastName = lastName,
    facultyId = facultyId,
    facultyName = null,   // Not in Entity, obtained via JOIN query
    direction = direction,
    avatar = avatar
)

/**
 * domain.Student -> StudentEntity
 * Converts Domain model to DB Entity format.
 */
fun Student.toEntity(): StudentEntity = StudentEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    facultyId = facultyId,
    direction = direction,
    avatar = avatar
)

/**
 * StudentWithFaculty (result of JOIN) -> domain.Student
 * Converts to domain model and preserves facultyName.
 */
fun StudentWithFaculty.toDomain(): Student = Student(
    id = id,
    firstName = firstName,
    lastName = lastName,
    facultyId = facultyId,
    facultyName = facultyName,
    direction = direction,
    avatar = avatar
)

/**
 * StudentWithFaculty -> SimpleStudent (lightweight DTO for UI)
 * Used to display only required fields in the UI.
 */
fun StudentWithFaculty.toSimpleStudent(): SimpleStudent = SimpleStudent(
    id = id,
    firstName = firstName,
    lastName = lastName,
    facultyName = facultyName,
    direction = direction
)
