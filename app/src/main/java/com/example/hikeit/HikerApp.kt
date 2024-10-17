package com.example.hikeit

import android.app.Application
import com.example.hikeit.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class HikerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HikerApp)
            androidLogger()

            modules(appModule)
        }
    }
}