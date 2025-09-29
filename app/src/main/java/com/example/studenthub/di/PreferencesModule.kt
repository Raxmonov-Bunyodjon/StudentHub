package com.example.studenthub.di

import android.content.Context
import com.example.studenthub.data.local.preferences.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    /**
     * Provides UserPreferences as a singleton instance.
     * @param context - Application context provided by Hilt
     * @return UserPreferences instance
     *
     * 🔹 Through this module, repositories or ViewModels
     * can work with UserPreferences via Hilt injection
     */
    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }
}