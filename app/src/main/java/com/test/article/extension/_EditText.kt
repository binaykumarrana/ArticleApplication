package com.test.article.extension

import android.graphics.PorterDuff
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.test.article.R

fun AppCompatEditText.displayMode() {
    apply {
        isEnabled = false
        isClickable = false
        isFocusable = false
        isFocusableInTouchMode = false
        background.mutate().setColorFilter(
            ContextCompat.getColor(context, R.color.white),
            PorterDuff.Mode.SRC_ATOP
        )
    }
}

fun AppCompatEditText.editMode() {
    apply {
        isEnabled = true
        isClickable = true
        isFocusable = true
        isFocusableInTouchMode = true
        background.mutate().setColorFilter(
            ContextCompat.getColor(context, R.color.grey),
            PorterDuff.Mode.SRC_ATOP
        )
    }
}