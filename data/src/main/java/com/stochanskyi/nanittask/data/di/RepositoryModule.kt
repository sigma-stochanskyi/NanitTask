package com.stochanskyi.nanittask.data.di

import com.stochanskyi.nanittask.data.repositories.ProfileRepositoryImpl
import com.stochanskyi.nanittask.domain.feature.profile.ProfileRepository
import org.koin.dsl.module

val RepositoryModule = module {
    factory<ProfileRepository> { ProfileRepositoryImpl(get()) }
}