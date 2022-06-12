package com.stochanskyi.nanittask.androidcore.data.files

import android.graphics.Bitmap
import java.io.File
import java.io.IOException

fun File.writeBitmap(
    bitmap: Bitmap,
    quality: Int = 100
): Boolean {
    return try {
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream())
        true
    } catch (e: IOException) {
        false
    }
}