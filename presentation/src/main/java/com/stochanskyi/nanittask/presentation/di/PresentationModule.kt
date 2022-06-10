package com.stochanskyi.nanittask.presentation.di

import com.stochanskyi.nanittask.presentation.ui.features.profile.ProfileViewModel
import com.stochanskyi.nanittask.presentation.ui.features.profile.ProfileViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val PresentationModule = module {

    viewModel<ProfileViewModel> { ProfileViewModelImpl() }

}