package com.example.studenthub.domain.model

/**
 * Student — Domain layer model.
 * Represents student information used in the business logic and UI layers.
 *
 * Difference between Entity and Domain model:
 * - Entity: used for database persistence (Room DB)
 * - Domain model: used in the business logic and UI representation
 */
data class Student (
    val id: Long,              // Unique identifier of the student (aligned with Room DB)
    val firstName: String,     // Student's first name
    val lastName: String,      // Student's last name
    val facultyId: Long,       // ID of the faculty the student belongs to (aligned with Room DB)
    val direction: String?,    // Student's field of study or specialization
    val avatar: String?,       // Student's avatar image (URL or file path, nullable)
    val facultyName: String?   // Faculty name (result of JOIN, nullable)
)