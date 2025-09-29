package com.example.studenthub.data.local


/**
 * StudentWithFaculty — a model that combines Student and Faculty data.
 * This class is used in Room to retrieve results from JOIN queries.
 */
data class StudentWithFaculty(

    /**
     * id — unique identifier for the student.
     * This corresponds to the Primary Key in the `students` table.
     */
    val id: Long,

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
     * This corresponds to the id in the `faculties` table.
     */
    val facultyId: Long,

    /**
     * facultyName — the name of the faculty.
     * Retrieved from the `faculties` table through a JOIN.
     */
    val facultyName: String,

    /**
     * direction — the student's field of study
     * (e.g., IT, Physics, Philology).
     */
    val direction: String,

    /**
     * avatar — the student's profile picture (URL or file path).
     * Nullable → can be null if no picture is provided.
     */
    val avatar: String?
)