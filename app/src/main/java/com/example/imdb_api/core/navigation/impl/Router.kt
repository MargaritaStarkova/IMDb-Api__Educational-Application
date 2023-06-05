package com.example.imdb_api.core.navigation.impl

import androidx.fragment.app.Fragment
import com.example.imdb_api.core.navigation.api.INavigatorHolder
import com.example.imdb_api.core.navigation.api.IRouter

class Router : IRouter {
    
    override val navigatorHolder: INavigatorHolder = NavigatorHolder()
    
    override fun openFragment(fragment: Fragment) {
        navigatorHolder.openFragment(fragment)
    }
}