package com.example.imdb_api.ui.root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.imdb_api.R
import com.example.imdb_api.databinding.ActivityRootBinding
import com.example.imdb_api.ui.movies.MoviesFragment

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<MoviesFragment>(R.id.rootFragmentContainerView)
            }
        }
    }
}