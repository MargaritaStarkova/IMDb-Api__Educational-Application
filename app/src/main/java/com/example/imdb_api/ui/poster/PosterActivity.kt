package com.example.imdb_api.ui.poster

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.imdb_api.R
import com.example.imdb_api.presentation.poster.PosterView
import com.example.imdb_api.presentation.poster.PosterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PosterActivity : AppCompatActivity(), PosterView {

    private lateinit var poster: ImageView
    private lateinit var url: String
    private val viewModel by viewModel<PosterViewModel> {
        parametersOf(this, url)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)

        poster = findViewById(R.id.poster)

        url = intent.extras?.getString("poster", "") ?: ""
        viewModel.onCreate()
    }

    override fun setupPosterImage(url: String) {
        Glide
            .with(this)
            .load(url)
            .into(poster)
    }
}