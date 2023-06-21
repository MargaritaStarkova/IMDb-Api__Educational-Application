package com.example.imdb_api.core.root

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.imdb_api.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class RootActivity : AppCompatActivity(R.layout.activity_root) {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        
        
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailsRootFragment, R.id.movieCastFragment -> {
                    bottomNavigationView.visibility = View.GONE
                }
                
                else -> bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
    
    fun animateBottomNavigationView() {
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
    }
}

