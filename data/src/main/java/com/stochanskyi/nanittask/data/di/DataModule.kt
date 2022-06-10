package com.stochanskyi.nanittask.data.di

import com.stochanskyi.nanittask.androidcore.data.files.AppFileProvider
import com.stochanskyi.nanittask.androidcore.data.files.AppFileProviderImpl
import com.stochanskyi.nanittask.androidcore.data.textformatter.TextFormatter
import com.stochanskyi.nanittask.androidcore.data.textformatter.TextFormatterImpl
import org.koin.dsl.module

val DataModule = module {

    factory<TextFormatter> { TextFormatterImpl() }

    factory<AppFileProvider> { AppFileProviderImpl(get()) }

}