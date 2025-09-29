package com.example.studenthub.utils

/**
 * 🔹 Constants
 *
 * Utility class to store static values and constant variables used throughout the app.
 *
 * Examples: SharedPreferences keys, static URLs, request codes, etc.
 *
 * This class is used without creating an instance (via companion object).
 */
class Constants {

    companion object{
        // 🔹 SharedPreferences file name
        const val PREFS_NAME = "student_hub_prefs"

        // 🔹 Key to store the logged-in username
        const val KEY_USERNAME = "key_username"

        // 🔹 Default avatar URL for students
        const val DEFAULT_AVATAR_URL = "https://example.com/default_avatar.png"

        // 🔹 Intent request codes
        const val REQUEST_PICK_IMAGE = 1001

        // 🔹 Base API URL (if using a REST API)
        const val BASE_API_URL = "https://api.studenthub.com/"

        /**
         * 🔹 Function: getStudentFullName
         * Combines the first name and last name of a student and returns the full name.
         *
         * @param firstName Student's first name
         * @param lastName Student's last name
         * @return Full name as a string
         */
        fun getStudentFullName(firstName: String, lastName: String): String {
            return "$firstName $lastName"
        }
    }
}