package com.example.studenthub.di


import android.content.Context
import androidx.room.Room
import com.example.studenthub.data.local.dao.FacultyDao
import com.example.studenthub.data.local.dao.StudentDao
import com.example.studenthub.data.local.dao.UserDao
import com.example.studenthub.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides AppDatabase as a singleton instance.
     * @param context - Application context provided by Hilt
     * @return AppDatabase instance
     */
    @Provides
    @Singleton
    fun provodeDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "studenthub_db"  // Database name, stored as a file
        ).build()
    }

    /**
     * Provides UserDao.
     * Used in repositories or ViewModels via Hilt injection.
     */
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()


    /**
     * Provides StudentDao.
     */
    @Provides
    fun provideStudentDao(db: AppDatabase): StudentDao = db.studentDao()

    /**
     * Provides FacultyDao.
     */
    @Provides
    fun provideFacultyDao(db: AppDatabase): FacultyDao = db.facultyDao()



}
