package com.example.imdb_api.core.navigation.impl

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.imdb_api.core.navigation.api.INavigator

class Navigator(
    override val fragmentContainerViewId: Int,
    override val fragmentManager: FragmentManager,
) : INavigator {
    
    override fun openFragment(fragment: Fragment) {
        fragmentManager.commit {
            replace(fragmentContainerViewId, fragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }
}