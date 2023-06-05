package com.example.imdb_api.core.navigation.api

import androidx.fragment.app.Fragment

/**
 * Сущность для хранения ссылки на INavigator.
 */
interface INavigatorHolder {
    
    fun attachNavigator(navigator: INavigator)
    fun detachNavigator()
    fun openFragment(fragment: Fragment)

}
