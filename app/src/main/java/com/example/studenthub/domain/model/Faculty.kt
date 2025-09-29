package com.example.studenthub.domain.model


/**
 * Faculty — Domain layer model.
 * Represents faculty information used within the business logic and UI layers.
 *
 * Difference between Entity and Domain model:
 * - Entity: used for database persistence (Room DB)
 * - Domain model: used for business logic and UI representation
 */
data class Faculty(
    val id: Long,    // Unique identifier of the faculty (aligned with Room DB)
    val name: String   // Name of the faculty
)