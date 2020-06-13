package com.test.article.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.article.R
import com.test.article.ui.DialogCallback

fun showErrorDialog(context: Context) {
    MaterialAlertDialogBuilder(context)
        .setTitle(context.getString(R.string.app_name))
        .setMessage(context.getString(R.string.error_msg))
        .setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}

fun showAlertDialog(context: Context,title:String,listener:DialogCallback,message:String) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
            listener.callSuccess()
        }
        .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}