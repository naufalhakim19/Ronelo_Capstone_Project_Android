package com.example.ronelo.consultMenu.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Helper {
    fun closeKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}