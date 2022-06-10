package com.stochanskyi.nanittask.app

import android.app.Application
import com.stochanskyi.nanittask.data.di.DataModule
import com.stochanskyi.nanittask.data.di.RepositoryModule
import com.stochanskyi.nanittask.domain.di.UseCaseModule
import com.stochanskyi.nanittask.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NaniTaskApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@NaniTaskApplication)
            modules(
                DataModule,
                RepositoryModule,
                UseCaseModule,
                PresentationModule,
            )
        }
    }

}