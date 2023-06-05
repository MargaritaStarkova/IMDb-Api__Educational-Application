package com.example.imdb_api.core.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imdb_api.R
import com.example.imdb_api.core.navigation.api.INavigatorHolder
import com.example.imdb_api.core.navigation.impl.Navigator
import com.example.imdb_api.databinding.ActivityRootBinding
import com.example.imdb_api.ui.movies.MoviesFragment
import org.koin.android.ext.android.inject

class RootActivity : AppCompatActivity() {
    
    // Заинжектили NavigatorHolder,
    // чтобы прикрепить к нему Navigator
    private val navigatorHolder: INavigatorHolder by inject()
    
    // Создали Navigator
    private val navigator = Navigator(
        fragmentContainerViewId = R.id.rootFragmentContainerView,
        fragmentManager = supportFragmentManager
    )
    
    // Прикрепляем Navigator к NavigatorHolder
    override fun onResume() {
        super.onResume()
        
        navigatorHolder.attachNavigator(navigator)
    }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Привязываем вёрстку к экрану
        val binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        if (savedInstanceState == null) {
            
            /* supportFragmentManager.commit {
                add<MoviesFragment>(R.id.rootFragmentContainerView) */
            
            // С помощью навигатора открываем первый экран
            navigator.openFragment(
                MoviesFragment()
            )
        }
    }
    
    // Открепляем Navigator от NavigatorHolder
    override fun onPause() {
        super.onPause()
        
        navigatorHolder.detachNavigator()
    }
}
