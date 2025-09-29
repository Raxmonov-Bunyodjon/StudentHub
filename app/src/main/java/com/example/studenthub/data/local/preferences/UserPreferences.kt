package com.example.studenthub.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Singleton DataStore extension property.
 * Creates a "user_prefs" preferences file, shared across the app.
 */
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

/**
 * UserPreferences — class for storing and retrieving user data
 * (e.g., username) using DataStore.
 */
class UserPreferences (private val context: Context) {

    companion object {
        /** Key for storing the username inside preferences. */
        val KEY_USERNAME = stringPreferencesKey("user_username")
    }

    /** Save username into DataStore. */
    suspend fun saveUsername(username: String){
        context.dataStore.edit { prefs ->
            prefs[KEY_USERNAME] = username
        }
    }

    /** Observe the stored username as a Flow<String?>. */
    val userUsernameFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[KEY_USERNAME] }

    /** Clear all stored data (used for logout). */
    suspend fun clearUser() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}