package com.stochanskyi.nanittask.androidcore.data.textformatter

import android.content.Context
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

interface TextFormatter {
    fun formatMillisToFullString(localDate: LocalDate): String
}

class TextFormatterImpl : TextFormatter {

    override fun formatMillisToFullString(localDate: LocalDate): String {
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        return localDate.format(formatter)
    }
}