package com.example.studenthub.ui.main.student

/**
 * 🔹 SimpleStudent
 *
 * This data class is intended for displaying students in the UI.
 * Unlike the business logic Student model:
 * - Only fields required for the UI are included
 * - Faculty name and direction are added as Strings for display
 *
 * ⚡ Purpose: lightweight model for fast rendering in RecyclerView or other UI components.
 */
data class SimpleStudent(
    val id: Long,              // Unique ID of the student (for DB reference)
    val firstName: String,     // Student's first name
    val lastName: String,      // Student's last name
    val facultyName: String,   // Student's faculty (for UI display)
    val direction: String      // Student's direction/major (for UI display)
)
