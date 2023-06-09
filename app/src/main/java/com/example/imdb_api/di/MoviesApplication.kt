package com.example.imdb_api.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class MoviesApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        GlobalContext.startKoin {
            androidContext(this@MoviesApplication)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
    }
}