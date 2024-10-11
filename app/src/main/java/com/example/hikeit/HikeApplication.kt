package com.example.hikeit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HikeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}