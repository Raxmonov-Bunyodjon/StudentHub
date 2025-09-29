package com.example.studenthub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * FacultyEntity — Entity class for Room.
 * This class represents the "faculties" table.
 */
@Entity(tableName = "faculties")
data class FacultyEntity(

    /**
     * id — unique identifier for the faculty (Primary Key).
     * autoGenerate = true → Room automatically generates the id whenever a new faculty is added.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /**
     * name — the name of the faculty (e.g., "Computer Science", "Philology").
     */
    val name: String
)
