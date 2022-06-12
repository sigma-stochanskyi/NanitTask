package com.stochanskyi.nanittask.domain.di

import com.stochanskyi.nanittask.domain.feature.profile.*
import org.koin.dsl.module

val UseCaseModule = module {

    factory<GetProfileUseCase> { GetProfileUseCaseImpl(get()) }

    factory<SetProfileUseCase> { SetProfileUseCaseImpl(get()) }

    factory<CalculateAgeUseCase> { CalculateAgeUseCaseImpl() }

    factory<SetProfileImageUseCase> { SetProfileImageUseCaseImpl(get()) }

}