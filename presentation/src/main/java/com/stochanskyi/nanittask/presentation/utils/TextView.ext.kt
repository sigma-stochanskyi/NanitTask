package com.stochanskyi.nanittask.presentation.utils

import android.widget.TextView

fun TextView.setTextIfChanged(newText: CharSequence?) {
    if (newText.toString() == this.text.toString()) return
    text = newText
}