package com.example.studenthub.ui.auth

import android.text.Editable
import android.text.TextWatcher

/**
 * SimpleTextWatcher — A simplified wrapper for the TextWatcher interface.
 * Designed to work only with the `onTextChanged` callback.
 *
 * @param onTextChanged A lambda function that is triggered every time the text changes.
 */
class SimpleTextWatcher(private val onTextChanged: (String?) -> Unit) : TextWatcher {
    /**
     * Called before the text is changed.
     * No action is performed here.
     */
    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    /**
     * Called while the text is being changed.
     * Passes the updated text to the provided lambda.
     */
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged(s?.toString())
    }

    /**
     * Called after the text has been changed.
     * No action is performed here.
     */
    override fun afterTextChanged(s: Editable?) {}
}

