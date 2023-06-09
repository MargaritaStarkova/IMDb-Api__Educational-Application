package com.example.imdb_api.ui.movie_cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdb_api.databinding.FragmentMoviesCastBinding
import com.example.imdb_api.presentation.cast.CastViewModel
import com.example.imdb_api.ui.details.DetailsItemFragment
import com.example.imdb_api.ui.models.CastState
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieCastFragment : Fragment() {
    
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentMoviesCastBinding.inflate(
            layoutInflater
        )
    }
    
    private val viewModel: CastViewModel by viewModel {
        parametersOf(arguments?.getString(DetailsItemFragment.MOVIE_ID) ?: "")
    }
    
    private val adapter = ListDelegationAdapter(
        movieCastHeaderDelegate(), movieCastPersonDelegate()
    )
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.moviesCastRecyclerView.adapter = adapter
        binding.moviesCastRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        viewModel
            .observeState()
            .observe(viewLifecycleOwner) { state ->
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
    
    companion object {
    
        private const val MOVIE_ID = "MOVIE_ID"
    
        fun createArgs(id: String): Bundle = bundleOf(
            MOVIE_ID to id
        )
    }
}