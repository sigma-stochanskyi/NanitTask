package com.stochanskyi.nanittask.androidcore.data.files

import android.content.Context
import java.io.File

interface AppFileProvider {
    fun cameraImageFile(): File
    fun shareAnniversaryFile(): File
}

class AppFileProviderImpl(
    private val context: Context
) : AppFileProvider{

    companion object {
        private const val CAMERA_IMAGE_FILE_NAME = "camera_image.jpeg"
        private const val SHARE_ANNIVERSARY_IMAGE_FILE_NAME = "anniversary_image.jpeg"
    }

    private val internalFilesDir
        get() = context.filesDir

    override fun cameraImageFile(): File {
        return createInternalFile(CAMERA_IMAGE_FILE_NAME)
    }

    override fun shareAnniversaryFile(): File {
        return createInternalFile(SHARE_ANNIVERSARY_IMAGE_FILE_NAME)
    }

    private fun createInternalFile(fileName: String): File {
        return File(internalFilesDir, fileName).apply {
            createIfNotExist()
        }
    }

    private fun File.createIfNotExist() {
        if (!exists()) createNewFile()
    }
}