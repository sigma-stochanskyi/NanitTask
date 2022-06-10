package com.stochanskyi.nanittask.domain.di

import com.stochanskyi.nanittask.domain.feature.profile.GetProfileUseCase
import com.stochanskyi.nanittask.domain.feature.profile.GetProfileUseCaseImpl
import com.stochanskyi.nanittask.domain.feature.profile.SetProfileUseCase
import com.stochanskyi.nanittask.domain.feature.profile.SetProfileUseCaseImpl
import org.koin.dsl.module

val UseCaseModule = module {

    factory<GetProfileUseCase> { GetProfileUseCaseImpl(get()) }

    factory<SetProfileUseCase> { SetProfileUseCaseImpl(get()) }

}