package com.example.imdb_api.core.navigation.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Navigator — сущность для работы с FragmentManager.
 */
interface INavigator {
    
    val fragmentContainerViewId: Int
    val fragmentManager: FragmentManager
    
    fun openFragment(fragment: Fragment)

}
