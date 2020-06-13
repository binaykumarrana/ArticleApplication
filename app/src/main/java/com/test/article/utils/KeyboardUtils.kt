package com.test.article.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun showKeyboard(view: View, context: Context){
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

fun hideKeyboard(view: View, context: Context) {
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}