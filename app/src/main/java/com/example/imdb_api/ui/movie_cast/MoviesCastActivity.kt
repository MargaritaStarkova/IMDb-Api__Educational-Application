package com.example.imdb_api.ui.movie_cast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imdb_api.data.dto.cast.MovieCastResponse
import com.example.imdb_api.databinding.ActivityMoviesCastBinding
import com.example.imdb_api.presentation.cast.CastViewModel
import com.example.imdb_api.ui.models.CastState
import com.example.imdb_api.ui.poster.fragments.DetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MoviesCastActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMoviesCastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesCastBinding.inflate(layoutInflater)
        setContentView(binding.root)
    
        val movieId = intent.getStringExtra(DetailsFragment.MOVIE_ID) ?: ""
    
        val viewModel: CastViewModel by viewModel {
            parametersOf(movieId)
        }
    
        viewModel
            .observeState()
            .observe(this) { state ->
                when (state) {
                    is CastState.Content -> showDetails(state.movie)
                    is CastState.Error -> showErrorMessage(state.message)
                }
            }
    }
    
    
        private fun showDetails(movie: MovieCastResponse) {
            TODO("Not yet implemented")
        }
    
        private fun showErrorMessage(message: String) {
            TODO("Not yet implemented")
        }
    
    
    }