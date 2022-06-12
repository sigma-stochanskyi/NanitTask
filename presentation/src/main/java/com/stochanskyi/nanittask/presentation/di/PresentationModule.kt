package com.stochanskyi.nanittask.presentation.di

import com.stochanskyi.nanittask.presentation.ui.features.birthday.BirthdayViewModel
import com.stochanskyi.nanittask.presentation.ui.features.birthday.BirthdayViewModelImpl
import com.stochanskyi.nanittask.presentation.ui.features.birthday.appearance.BirthdayViewAppearancesDefinition
import com.stochanskyi.nanittask.presentation.ui.features.birthday.appearance.BirthdayViewAppearancesDefinitionImpl
import com.stochanskyi.nanittask.presentation.ui.features.profile.ProfileViewModel
import com.stochanskyi.nanittask.presentation.ui.features.profile.ProfileViewModelImpl
import com.stochanskyi.nanittask.presentation.utils.activity_contracts.TakeOrPickImageContract
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val PresentationModule = module {

    viewModel<ProfileViewModel> { ProfileViewModelImpl(get(), get()) }

    viewModel<BirthdayViewModel> { BirthdayViewModelImpl(get(), get(), get()) }

    factory<BirthdayViewAppearancesDefinition> { BirthdayViewAppearancesDefinitionImpl() }

    factory { (titleRes: Int) ->
        TakeOrPickImageContract(
            appContext = get(),
            titleRes = titleRes
        )
    }

}