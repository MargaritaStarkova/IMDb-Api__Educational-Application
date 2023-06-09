package com.example.imdb_api.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.imdb_api.R
import com.example.imdb_api.databinding.FragmentItemDetailsBinding
import com.example.imdb_api.domain.models.MovieDetails
import com.example.imdb_api.presentation.poster.DetailsViewModel
import com.example.imdb_api.ui.models.DetailsState
import com.example.imdb_api.ui.movie_cast.MovieCastFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsItemFragment : Fragment() {
    
    private val detailsViewModel: DetailsViewModel by viewModel {
        parametersOf(requireArguments().getString(MOVIE_ID))
    }
    
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentItemDetailsBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        detailsViewModel
            .observeState()
            .observe(viewLifecycleOwner) { state ->
                when (state) {
                    is DetailsState.Content -> showDetails(state.movie)
                    is DetailsState.Error -> showErrorMessage(state.message)
                }
            }
        
        binding.showCastBtn.setOnClickListener {
            
            findNavController().navigate(
                R.id.action_detailsRootFragment_to_movieCastFragment,
                MovieCastFragment.createArgs(requireArguments().getString(MOVIE_ID).orEmpty())
            )
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
    
    fun newInstance(id: String) = DetailsItemFragment().apply {
        arguments = Bundle().apply {
            putString(MOVIE_ID, id)
        }
    }
}
}