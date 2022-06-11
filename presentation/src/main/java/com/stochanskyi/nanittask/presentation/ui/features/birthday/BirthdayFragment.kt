package com.stochanskyi.nanittask.presentation.ui.features.birthday

import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.ImageLoader
import coil.request.CachePolicy
import com.stochanskyi.nanittask.presentation.R
import com.stochanskyi.nanittask.presentation.databinding.FragmentBirthdayBinding
import com.stochanskyi.nanittask.presentation.ui.features.birthday.appearance.BirthdayViewAppearance
import com.stochanskyi.nanittask.presentation.utils.addSimpleOnLayoutChangeListener
import com.stochanskyi.nanittask.presentation.utils.constraintCircleRadius
import com.stochanskyi.nanittask.presentation.utils.setDrawableResIfMissing
import org.koin.androidx.viewmodel.ext.android.viewModel

class BirthdayFragment : Fragment(R.layout.fragment_birthday) {

    private val model: BirthdayViewModel by viewModel()

    private val binding: FragmentBirthdayBinding by viewBinding(FragmentBirthdayBinding::bind)

    private val imageLoader by lazy {
        ImageLoader.Builder(requireContext())
            .memoryCachePolicy(CachePolicy.DISABLED)
            .crossfade(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        observeModel()
    }

    private fun initViews() = with(binding) {
        imageProfile.addSimpleOnLayoutChangeListener {
            if (imageProfile.height == imageCamera.constraintCircleRadius) {
                return@addSimpleOnLayoutChangeListener
            }
            imageCamera.post {
                imageCamera.constraintCircleRadius =
                    (imageProfile.height / 2) - imageProfile.paddingEnd
            }
        }
    }

    private fun observeModel() = with(model) {
        viewAppearanceLiveData.observe(viewLifecycleOwner) {
            it.apply()
        }
    }

    private fun BirthdayViewAppearance.apply() = with(binding) {
        layoutRoot.setBackgroundColor(requireContext().getColor(backgroundColorRes))
        imageBackground.setImageResource(backgroundImageRes)

        imageProfile.setDrawableResIfMissing(profileImagePlaceholderRes)
        imageLoader
            .fallback(profileImagePlaceholderRes)
            .error(profileImagePlaceholderRes)

        imageProfile.setStrokeColorResource(profileImageStrokeColorRes)

        DrawableCompat.setTint(
            imageCamera.background,
            requireContext().getColor(cameraImageBackgroundColorRes)
        )
    }
}