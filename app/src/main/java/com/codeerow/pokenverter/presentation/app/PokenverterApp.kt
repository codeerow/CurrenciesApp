package com.codeerow.pokenverter.presentation.app

import android.app.Application
import com.codeerow.pokenverter.presentation.app.di.app
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber


class PokenverterApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
        initLogging()
    }

    private fun initDependencyInjection() {
        startKoin {
            androidLogger()
            androidContext(this@PokenverterApp)
            modules(app)
        }
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }
}