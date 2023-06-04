package com.example.imdb_api.ui.movie_cast

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdb_api.data.dto.cast.MoviesFullCastResponse
import com.example.imdb_api.databinding.ActivityMoviesCastBinding
import com.example.imdb_api.domain.models.MovieCast
import com.example.imdb_api.presentation.cast.CastViewModel
import com.example.imdb_api.ui.models.CastState
import com.example.imdb_api.ui.poster.fragments.DetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MoviesCastActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMoviesCastBinding
    
    private val viewModel: CastViewModel by viewModel {
        parametersOf(intent.getStringExtra(DetailsFragment.MOVIE_ID) ?: "")
    }
    private val adapter = MoviesCastAdapter()
    
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesCastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.moviesCastRecyclerView.adapter = adapter
        binding.moviesCastRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel
            .observeState()
            .observe(this) { state ->
                when (state) {
                    is CastState.Content -> showCast(state)
                    is CastState.Error -> showErrorMessage(state.message)
                    CastState.Loading -> showLoading()
                }
            }
    }
    
    private fun showLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            errorMessageTextView.visibility = View.GONE
            contentContainer.visibility = View.GONE
        }
    }
    
    private fun showCast(state: CastState.Content) {
        binding.apply {
            progressBar.visibility = View.GONE
            errorMessageTextView.visibility = View.GONE
            contentContainer.visibility = View.VISIBLE
            
            movieTitle.text = state.fullTitle
            adapter.items = state.items
            adapter.notifyDataSetChanged()
            
        }
    }
    
    private fun showErrorMessage(message: String) {
        binding.apply {
            progressBar.visibility = View.GONE
            errorMessageTextView.visibility = View.VISIBLE
            contentContainer.visibility = View.GONE
            
            errorMessageTextView.text = message
        }
    }
    
    
    }