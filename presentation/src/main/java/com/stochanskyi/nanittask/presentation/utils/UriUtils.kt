package com.stochanskyi.nanittask.presentation.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.stochanskyi.nanittask.presentation.R
import java.io.File

fun File.uriWithFileProvider(context: Context): Uri {
    return FileProvider.getUriForFile(
        context,
        context.getString(R.string.file_provider_authority),
        this
    )
}

fun Uri.grantReadPermission(context: Context) {
    context.applicationContext.contentResolver.takePersistableUriPermission(
        this,
        Intent.FLAG_GRANT_READ_URI_PERMISSION
    )
}