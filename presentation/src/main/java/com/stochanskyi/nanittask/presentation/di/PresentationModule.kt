package com.stochanskyi.nanittask.presentation.di

import com.stochanskyi.nanittask.presentation.ui.features.profile.ProfileViewModel
import com.stochanskyi.nanittask.presentation.ui.features.profile.ProfileViewModelImpl
import com.stochanskyi.nanittask.presentation.utils.activity_contracts.TakeOrPickImageContract
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val PresentationModule = module {

    viewModel<ProfileViewModel> { ProfileViewModelImpl() }

    factory { (titleRes: Int) ->
        TakeOrPickImageContract(
            appContext = get(),
            titleRes = titleRes
        )
    }

}