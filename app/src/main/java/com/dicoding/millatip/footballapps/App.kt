package com.dicoding.millatip.footballapps

import android.app.Application
import com.dicoding.millatip.footballapps.di.appModule
import com.dicoding.millatip.footballapps.di.networkModule
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(networkModule, appModule))
    }
}