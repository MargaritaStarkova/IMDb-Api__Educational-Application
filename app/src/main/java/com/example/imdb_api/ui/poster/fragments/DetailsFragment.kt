package com.example.imdb_api.ui.poster.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imdb_api.databinding.DetailsFragmentBinding
import com.example.imdb_api.domain.models.MovieDetails
import com.example.imdb_api.presentation.poster.DetailsViewModel
import com.example.imdb_api.ui.models.DetailsState
import com.example.imdb_api.ui.movie_cast.MoviesCastActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {
    

    
    private val detailsViewModel: DetailsViewModel by viewModel {
        parametersOf(requireArguments().getString(MOVIE_ID))
    }
    
    private lateinit var binding: DetailsFragmentBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        detailsViewModel.observeState().observe(viewLifecycleOwner) { state ->
            when(state) {
                is DetailsState.Content -> showDetails(state.movie)
                is DetailsState.Error -> showErrorMessage(state.message)
            }
        }
        
        binding.showCastBtn.setOnClickListener {
            val intent = Intent(context, MoviesCastActivity::class.java)
            intent.putExtra(MOVIE_ID, requireArguments().getString(MOVIE_ID))
            startActivity(intent)
        }
    }
    
    private fun showErrorMessage(message: String) {
        binding.apply {
            detaiils.visibility = View.GONE
            errorMessage.visibility = View.VISIBLE
            errorMessage.text = message
        }
    }
    
    private fun showDetails(movieDetails: MovieDetails) {
        binding.apply {
            detaiils.visibility = View.VISIBLE
            errorMessage.visibility = View.GONE
            title.text = movieDetails.title
            ratingValue.text = movieDetails.imDbRating
            yearValue.text = movieDetails.year
            countryValue.text = movieDetails.countries
            genreValue.text = movieDetails.genres
            directorValue.text = movieDetails.directors
            writerValue.text = movieDetails.writers
            castValue.text = movieDetails.stars
            plot.text = movieDetails.plot
        }
        
    }


companion object {
        const val MOVIE_ID = "MOVIE_ID"
        
        fun newInstance(id: String) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putString(MOVIE_ID, id)
            }
        }
    }
}