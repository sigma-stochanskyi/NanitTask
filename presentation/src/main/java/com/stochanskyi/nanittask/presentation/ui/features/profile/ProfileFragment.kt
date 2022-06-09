package com.stochanskyi.nanittask.presentation.ui.features.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.stochanskyi.nanittask.presentation.R
import com.stochanskyi.nanittask.presentation.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    companion object {
        private const val DATE_PICKER_DIALOG_TAG = "date_picker_dialog"
    }

    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() = with(binding) {
        editTextName.doAfterTextChanged {
            // TODO update name
        }

        editTextDate.keyListener = null
        editTextDate.setOnClickListener {
            openCalendarDialog()
        }

        buttonShowBirthday.setOnClickListener {
            // TODO show birthday
        }
    }

    private fun openCalendarDialog() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.title_select_birthday))
            .build()

        picker.addOnPositiveButtonClickListener {
            // TODO update date
            Log.d("", "")
        }
        picker.show(childFragmentManager, DATE_PICKER_DIALOG_TAG)
    }
}