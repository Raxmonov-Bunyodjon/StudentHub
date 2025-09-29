package com.example.studenthub.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * 🔹 showSnackbar
 *
 * Extension function for View that allows showing a Snackbar on any View.
 *
 * Usage:
 * ```
 * binding.root.showSnackbar("Message text")
 * ```
 *
 * @param message The text to display in the Snackbar
 */
fun View.showSnackbar(message: String) {
    // Creates a Snackbar and shows it with LENGTH_LONG duration
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}