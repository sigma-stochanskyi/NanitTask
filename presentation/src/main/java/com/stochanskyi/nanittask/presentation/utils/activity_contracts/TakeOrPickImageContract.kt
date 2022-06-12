package com.stochanskyi.nanittask.presentation.utils.activity_contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.StringRes
import com.stochanskyi.nanittask.androidcore.data.utils.addIfNotNull
import com.stochanskyi.nanittask.presentation.utils.createCameraIntent
import com.stochanskyi.nanittask.presentation.utils.createGalleryPickerIntent
import com.stochanskyi.nanittask.presentation.utils.grantReadPermission

class TakeOrPickImageContract(
    private val appContext: Context,
    @StringRes private val titleRes: Int
) : ActivityResultContract<Uri, Uri?>() {

    private var cameraUri: Uri? = null

    override fun createIntent(context: Context, input: Uri): Intent {
        cameraUri = input
        val intents = mutableListOf<Intent>()

        intents.addIfNotNull(context.createCameraIntent(input))
        intents.addIfNotNull(context.createGalleryPickerIntent())

        return context.createIntentChooser(intents)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }

        intent?.data?.grantReadPermission(appContext)

        val resultUri = intent?.data ?: cameraUri
        cameraUri = null

        return resultUri
    }

    private fun Context.createIntentChooser(intents: List<Intent>): Intent {
        if (intents.isEmpty()) return Intent()

        val intent = Intent.createChooser(intents.first(), getString(titleRes))

        if (intents.size <= 1) return intent

        return intent.apply {
            putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.drop(1).toTypedArray())
        }
    }
}

