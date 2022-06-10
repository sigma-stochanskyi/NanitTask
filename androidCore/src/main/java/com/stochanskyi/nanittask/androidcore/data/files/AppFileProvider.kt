package com.stochanskyi.nanittask.androidcore.data.files

import android.content.Context
import java.io.File

interface AppFileProvider {
    fun cameraImageFile(): File
}

class AppFileProviderImpl(
    private val context: Context
) : AppFileProvider{

    companion object {
        private const val CAMERA_IMAGE_FILE_NAME = "camera_image.jpeg"
    }

    private val internalFilesDir
        get() = context.filesDir

    override fun cameraImageFile(): File {
        val file = File(internalFilesDir, CAMERA_IMAGE_FILE_NAME)
        return file.also { it.createIfNotExist() }
    }

    private fun File.createIfNotExist() {
        if (!exists()) createNewFile()
    }
}