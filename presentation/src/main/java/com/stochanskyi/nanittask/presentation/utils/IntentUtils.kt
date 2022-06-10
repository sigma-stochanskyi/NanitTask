package com.stochanskyi.nanittask.presentation.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

fun Context.createCameraIntent(uri: Uri): Intent? {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (intent.resolveActivity(packageManager) == null) return null

    return intent.apply {
        putExtra(MediaStore.EXTRA_OUTPUT, uri)
    }
}

fun Context.createGalleryPickerIntent(): Intent {
    return Intent(Intent.ACTION_OPEN_DOCUMENT, null)
        .apply { type = "image/*" }
}

