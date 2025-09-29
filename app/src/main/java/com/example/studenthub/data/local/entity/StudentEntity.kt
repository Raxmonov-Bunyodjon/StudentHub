package com.example.studenthub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * StudentEntity — Entity class for Room.
 * This class represents the "students" table.
 */
@Entity(tableName = "students")
data class StudentEntity(

    /**
     * id — unique identifier for the student (Primary Key).
     * autoGenerate = true → Room automatically generates the id whenever a new student is added.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /**
     * firstName — the student's first name.
     */
    val firstName: String,

    /**
     * lastName — the student's last name.
     */
    val lastName: String,

    /**
     * facultyId — indicates which faculty the student belongs to.
     * This references the id in the FacultyEntity table (acts as a foreign key).
     */
    val facultyId: Long,

    /**
     * direction — the student's field of study
     * (e.g., "Software Engineering", "Philology").
     */
    val direction: String?,

    /**
     * avatar — the student's profile picture (URL or file path).
     * Nullable → can be null if no picture is provided.
     */
    val avatar: String? = null
)
