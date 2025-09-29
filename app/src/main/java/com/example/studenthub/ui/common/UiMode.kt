package com.example.studenthub.ui.common

/**
 * Get students filtered by faculty ID.
 * The DAO returns Flow<List<StudentEntity>>, which is then mapped to the domain model.
 * Since Flow is used, any changes in the database will be reflected in the UI automatically.
 */
sealed class UiMode {
    object Add : UiMode()
    data class Edit(val id: Long) : UiMode()
}