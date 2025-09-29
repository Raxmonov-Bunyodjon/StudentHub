package com.example.studenthub

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * 🔹 StudentHub
 *
 * The Application class of the project.
 * This class runs when the Android app starts and
 * is used to initialize the Hilt dependency injection (DI) framework.
 *
 * @HiltAndroidApp annotation:
 * - Required for Dagger Hilt
 * - Marks the application as a DI container
 * - All Activities and Fragments annotated with @AndroidEntryPoint
 *   can receive their dependencies through this DI container
 */
@HiltAndroidApp
class StudentHub : Application()  {

    /**
     * 🔹 onCreate()
     *
     * Called when the application is launched.
     * The Hilt DI container is initialized here.
     * You can also place other global initialization code here
     */
    override fun onCreate() {
        super.onCreate()
        // Example: initialize logs, analytics, crashlytics, or other libraries
    }

}