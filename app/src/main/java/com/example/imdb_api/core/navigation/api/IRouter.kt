package com.example.imdb_api.core.navigation.api

import androidx.fragment.app.Fragment

/**
 * Router — это входная точка для фрагментов,
 * которые хотят открыть следующий экран.
 */
interface IRouter {
    val navigatorHolder : INavigatorHolder
    fun openFragment(fragment: Fragment)
}