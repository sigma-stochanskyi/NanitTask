package com.stochanskyi.nanittask.presentation.ui.features.birthday

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.CachePolicy
import com.stochanskyi.nanittask.androidcore.data.files.AppFileProvider
import com.stochanskyi.nanittask.androidcore.data.files.writeBitmap
import com.stochanskyi.nanittask.androidcore.data.utils.toRadians
import com.stochanskyi.nanittask.presentation.R
import com.stochanskyi.nanittask.presentation.databinding.FragmentBirthdayBinding
import com.stochanskyi.nanittask.presentation.ui.features.birthday.appearance.BirthdayViewAppearance
import com.stochanskyi.nanittask.presentation.ui.features.birthday.model.AgeInfoViewData
import com.stochanskyi.nanittask.presentation.utils.*
import com.stochanskyi.nanittask.presentation.utils.activity_contracts.TakeOrPickImageContract
import com.stochanskyi.nanittask.presentation.utils.insets.onApplyDispatchInsetsToAllChildren
import com.stochanskyi.nanittask.presentation.utils.insets.onApplyDispatchInsetsWithMargins
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.sin

class BirthdayFragment : Fragment(R.layout.fragment_birthday) {

    companion object {
        private const val CAMERA_IMAGE_ANGLE = 45f
    }

    private val model: BirthdayViewModel by viewModel()

    private val binding: FragmentBirthdayBinding by viewBinding(FragmentBirthdayBinding::bind)

    private val fileProvider: AppFileProvider by inject()

    private val imageLoaderBuilder by lazy {
        ImageLoader.Builder(requireContext())
            .memoryCachePolicy(CachePolicy.DISABLED)
            .crossfade(true)
    }

    private var imageLoader: ImageLoader? = null

    private val getOrPickImageContract: TakeOrPickImageContract by inject { parametersOf(R.string.title_pick_image) }

    private val getOrPickImage =
        registerForActivityResult(getOrPickImageContract) {
            onImagePickResult(it)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        applyInsets()
        observeModel()
    }

    private fun initViews() = with(binding) {
        imageProfile.addSimpleOnLayoutChangeListener {
            val radius = imageProfile.width / 2 - imageProfile.strokeWidth / 2
            val translate = sin(CAMERA_IMAGE_ANGLE.toRadians()) * radius

            imageCamera.translationX = translate
            imageCamera.translationY = -1 * translate
        }

        imageCamera.setOnClickListener {
            openImagePicker()
        }

        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        buttonShare.setOnClickListener {
            shareAnniversaryImage()
        }
    }

    private fun applyInsets() = with(binding) {
        root.onApplyDispatchInsetsToAllChildren()
        buttonBack.onApplyDispatchInsetsWithMargins()
        textTitle.onApplyDispatchInsetsWithMargins()
    }

    private fun observeModel() = with(model) {
        viewAppearanceLiveData.observe(viewLifecycleOwner) {
            it.apply()
        }
        nameLiveData.observe(viewLifecycleOwner) {
            setName(it)
        }
        ageInfoLiveData.observe(viewLifecycleOwner) {
            setAgeIngo(it)
        }
        imageLiveData.observe(viewLifecycleOwner) {
            setProfileImage(it)
        }
    }

    private fun BirthdayViewAppearance.apply() = with(binding) {
        layoutRoot.setBackgroundColor(requireContext().getColor(backgroundColorRes))
        imageBackground.setImageResource(backgroundImageRes)

        imageProfile.setDrawableResIfMissing(profileImagePlaceholderRes)
        imageLoader = imageLoaderBuilder
            .fallback(profileImagePlaceholderRes)
            .error(profileImagePlaceholderRes)
            .placeholder(profileImagePlaceholderRes)
            .build()

        imageProfile.setStrokeColorResource(profileImageStrokeColorRes)

        DrawableCompat.setTint(
            imageCamera.background,
            requireContext().getColor(cameraImageBackgroundColorRes)
        )
    }

    private fun setName(name: String) {
        binding.textTitle.text = getString(R.string.birthday_name_pattern, name)
    }

    private fun setAgeIngo(data: AgeInfoViewData) = with(binding) {
        viewAge.setAge(data.ageValue)

        textAgeUnit.text = resources.getQuantityString(
            data.ageUnitPluralRes,
            data.ageValue,
            data.ageValue
        )
    }

    private fun setProfileImage(uriString: String?) {
        val uri = uriString?.let { Uri.parse(it) } ?: return
        val loader = imageLoader ?: requireContext().imageLoader

        binding.imageProfile.load(uri, loader) {
            allowHardware(false)
            lifecycle(viewLifecycleOwner)
        }
    }

    private fun openImagePicker() {
        val cameraUri = fileProvider.cameraImageFile().uriWithFileProvider(requireContext())
        getOrPickImage.launch(cameraUri)
    }

    private fun onImagePickResult(result: Uri?) {
        result ?: return
        model.setImageUri(result.toString())
    }

    private fun shareAnniversaryImage() {
        val file = fileProvider.shareAnniversaryFile()
        file.writeBitmap(createShareBitmap())

        val uri = file.uriWithFileProvider(context ?: return)
        context?.launchSendIntent(uri)
    }

    private fun createShareBitmap(): Bitmap = with(binding) {
        return layoutRoot.drawToBitmapWithExcludingInvisible(
            listOf(imageCamera, buttonShare, buttonBack)
        )
    }
}