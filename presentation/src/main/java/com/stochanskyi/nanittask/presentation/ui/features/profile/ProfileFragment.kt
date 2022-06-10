package com.stochanskyi.nanittask.presentation.ui.features.profile

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.stochanskyi.nanittask.androidcore.data.files.AppFileProvider
import com.stochanskyi.nanittask.androidcore.data.textformatter.TextFormatter
import com.stochanskyi.nanittask.presentation.R
import com.stochanskyi.nanittask.presentation.databinding.FragmentProfileBinding
import com.stochanskyi.nanittask.presentation.utils.activity_contracts.TakeOrPickImageContract
import com.stochanskyi.nanittask.presentation.utils.uriWithFileProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    companion object {
        private const val DATE_PICKER_DIALOG_TAG = "date_picker_dialog"
    }

    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    private val model: ProfileViewModel by viewModel()

    private val textFormatter by inject<TextFormatter>()

    private val fileProvider: AppFileProvider by inject()

    private var cameraUri: String? = null

    private val getOrPickImageContract: TakeOrPickImageContract by inject { parametersOf(R.string.title_pick_image) }

    private val getOrPickImage =registerForActivityResult(getOrPickImageContract) {
            onImagePickResult(it)
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
            val cameraUri = fileProvider.cameraImageFile().uriWithFileProvider(requireContext())
            getOrPickImage.launch(cameraUri)
        }
    }

    private fun observeModel() = with(model) {
        openBirthdayEnabledLiveData.observe(viewLifecycleOwner) {
            binding.buttonShowBirthday.isInvisible = !it
        }
        openBirthdayLiveData.observe(viewLifecycleOwner) {
            openBirthdayScreen()
        }
        birthdayLiveData.observe(viewLifecycleOwner) {
            binding.editTextDate.setText(
                it?.let { textFormatter.formatMillisToFullString(it) } ?: ""
            )
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

    private fun onImagePickResult(result: Result<Uri?>?) {
        val uri = result?.getOrNull() ?: cameraUri ?: return

        model.setImageUri(uri.toString())
    }

    private fun openBirthdayScreen() {
        findNavController().navigate(R.id.action_profileFragment_to_birthdayFragment)
    }
}