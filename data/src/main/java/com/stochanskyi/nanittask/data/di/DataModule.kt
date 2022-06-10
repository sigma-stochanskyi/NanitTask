package com.stochanskyi.nanittask.data.di

import com.stochanskyi.nanittask.androidcore.data.files.AppFileProvider
import com.stochanskyi.nanittask.androidcore.data.files.AppFileProviderImpl
import com.stochanskyi.nanittask.androidcore.data.textformatter.TextFormatter
import com.stochanskyi.nanittask.androidcore.data.textformatter.TextFormatterImpl
import com.stochanskyi.nanittask.data.storage.ProfileDataStorage
import com.stochanskyi.nanittask.data.storage.ProfileDataStorageImpl
import org.koin.dsl.module
import java.net.DatagramSocketImpl

val DataModule = module {

    factory<TextFormatter> { TextFormatterImpl() }

    factory<AppFileProvider> { AppFileProviderImpl(get()) }

    single<ProfileDataStorage> { ProfileDataStorageImpl(get()) }

}