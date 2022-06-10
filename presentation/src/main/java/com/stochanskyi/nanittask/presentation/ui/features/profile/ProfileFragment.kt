package com.stochanskyi.nanittask.presentation.ui.features.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.request.CachePolicy
import com.google.android.material.datepicker.MaterialDatePicker
import com.stochanskyi.nanittask.androidcore.data.files.AppFileProvider
import com.stochanskyi.nanittask.androidcore.data.textformatter.TextFormatter
import com.stochanskyi.nanittask.presentation.R
import com.stochanskyi.nanittask.presentation.databinding.FragmentProfileBinding
import com.stochanskyi.nanittask.presentation.utils.activity_contracts.TakeOrPickImageContract
import com.stochanskyi.nanittask.presentation.utils.setTextIfChanged
import com.stochanskyi.nanittask.presentation.utils.uriWithFileProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDate

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    companion object {
        private const val DATE_PICKER_DIALOG_TAG = "date_picker_dialog"
    }

    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    private val model: ProfileViewModel by viewModel()

    private val textFormatter by inject<TextFormatter>()

    private val fileProvider: AppFileProvider by inject()

    private var cameraUri: Uri? = null

    private val getOrPickImageContract: TakeOrPickImageContract by inject { parametersOf(R.string.title_pick_image) }

    private val getOrPickImage =registerForActivityResult(getOrPickImageContract) {
            onImagePickResult(it)
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model.loadProfile()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        observeModel()
    }

    private fun initViews() = with(binding) {
        editTextName.doAfterTextChanged {
            model.setName(it.toString())
        }

        editTextDate.keyListener = null
        editTextDate.setOnClickListener {
            openCalendarDialog()
        }

        buttonShowBirthday.setOnClickListener {
            model.openBirthday()
        }
        image.setOnClickListener {
            openImagePicker()
        }
    }

    private fun observeModel() = with(model) {
        isOpenBirthdayEnabledLiveData.observe(viewLifecycleOwner) {
            binding.buttonShowBirthday.isInvisible = !it
        }
        openBirthdayLiveData.observe(viewLifecycleOwner) {
            openBirthdayScreen()
        }
        nameLiveData.observe(viewLifecycleOwner) {
            binding.editTextName.setTextIfChanged(it)
        }
        birthdayLiveData.observe(viewLifecycleOwner) {
            setDate(it)
        }
        imageUriLiveData.observe(viewLifecycleOwner) {
            setImage(it)
        }
    }

    private fun openCalendarDialog() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.title_select_birthday))
            .build()

        picker.addOnPositiveButtonClickListener {
            model.setDate(it)
        }
        picker.show(childFragmentManager, DATE_PICKER_DIALOG_TAG)
    }

    private fun openImagePicker() {
        cameraUri = fileProvider.cameraImageFile().uriWithFileProvider(requireContext())
            .also { getOrPickImage.launch(it) }
    }

    private fun onImagePickResult(result: Result<Uri?>?) {
        val uri = result?.getOrNull() ?: cameraUri ?: return

        model.setImageUri(uri.toString())
    }

    private fun setDate(date: LocalDate?) {
        val formattedDate = date?.let {
            textFormatter.formatMillisToFullString(it)
        } ?: ""

        binding.editTextDate.setText(formattedDate)
    }

    private fun setImage(imageUri: String?) {
        val uri = imageUri?.let { Uri.parse(imageUri) }

        binding.image.load(uri) {
            lifecycle(viewLifecycleOwner)
            fallback(R.drawable.ic_profile_image_placeholder)
            placeholder(R.drawable.ic_profile_image_placeholder)
            error(R.drawable.ic_profile_image_placeholder)
            memoryCachePolicy(CachePolicy.DISABLED)
        }
    }

    private fun openBirthdayScreen() {
        findNavController().navigate(R.id.action_profileFragment_to_birthdayFragment)
    }
}