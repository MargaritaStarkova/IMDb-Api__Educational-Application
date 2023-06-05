package com.example.imdb_api.di

import com.example.imdb_api.core.navigation.api.IRouter
import com.example.imdb_api.core.navigation.impl.Router
import org.koin.dsl.module

val navigationModule = module {
    val router = Router()
    
    single<IRouter> { router }
    single { router.navigatorHolder }
}

