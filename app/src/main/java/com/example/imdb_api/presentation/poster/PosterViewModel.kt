package com.example.imdb_api.presentation.poster

import androidx.lifecycle.ViewModel

class PosterViewModel(
    private val view: PosterView,
    private val url: String,
) : ViewModel() {
    fun onCreate(){
        view.setupPosterImage(url)
    }
}

