package com.example.imdb_api.core.navigation.impl

import androidx.fragment.app.Fragment
import com.example.imdb_api.core.navigation.api.INavigator
import com.example.imdb_api.core.navigation.api.INavigatorHolder

class NavigatorHolder : INavigatorHolder {
    
    private var navigator: INavigator? = null
    
    override fun attachNavigator(navigator: INavigator) {
        this.navigator = navigator
    }
    
    override fun detachNavigator() {
        this.navigator = null
    }
    
    override fun openFragment(fragment: Fragment) {
        navigator?.openFragment(fragment)
    }
}