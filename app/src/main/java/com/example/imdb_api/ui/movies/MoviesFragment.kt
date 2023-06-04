package com.example.imdb_api.ui.movies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdb_api.R
import com.example.imdb_api.databinding.FragmentMoviesBinding
import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.presentation.movies.MoviesSearchViewModel
import com.example.imdb_api.ui.models.MoviesState
import com.example.imdb_api.ui.details.DetailsRootFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {
    
    // private val binding = FragmentMoviesBinding.inflate(layoutInflater)
    //private lateinit var binding: FragmentMoviesBinding
    
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentMoviesBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModel<MoviesSearchViewModel>()
    private var textWatcher: TextWatcher? = null
    
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())
    
    private val adapter = MoviesAdapter(object : MoviesAdapter.MovieClickListener {
        override fun onMovieClick(movie: Movie) {
            if (clickDebounce()) {
/*                 val intent = Intent(requireContext(), DetailsRootFragment::class.java)
                intent.putExtra("poster", movie.image)
                intent.putExtra("id", movie.id)
                startActivity(intent) */
    
/*                 activity?.supportFragmentManager?.commit {
                    replace(R.id.rootFragmentContainerView, DetailsRootFragment.newInstance(movie.image, movie.id))
                    addToBackStack(null)
    
                } */
                
                parentFragmentManager.commit {
                    replace(
                        R.id.rootFragmentContainerView,
                        DetailsRootFragment.newInstance(movie.image, movie.id),
                        DetailsRootFragment.TAG)

                    addToBackStack(null)
                }
            }
        }
        
        override fun onFavoriteToggleClick(movie: Movie) {
            viewModel.toggleFavorite(movie)
        }
        
    })
    
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        //binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel
            .observeState()
            .observe(viewLifecycleOwner) { moviesState ->
                render(moviesState)
            }
        viewModel
            .observeShowToast()
            .observe(viewLifecycleOwner) { message ->
                showToastMessage(message)
            }
        
        binding.moviesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.moviesList.adapter = adapter
        
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }
            
            override fun afterTextChanged(s: Editable?) {}
        }
        textWatcher.let { binding.queryInput.addTextChangedListener(it) }
        
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        
        //удаляем listener
        textWatcher?.let { binding.queryInput.removeTextChangedListener(it) }
    }
    
    private fun showLoading() {
        binding.apply {
            placeholderMessage.visibility = View.GONE
            moviesList.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }
    
    private fun showError(errorMessage: String) {
        binding.apply {
            placeholderMessage.visibility = View.VISIBLE
            moviesList.visibility = View.GONE
            progressBar.visibility = View.GONE
            
            placeholderMessage.text = errorMessage
        }
    }
    
    private fun showContent(movies: List<Movie>) {
        
        binding.apply {
            placeholderMessage.visibility = View.GONE
            moviesList.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
        
        adapter.movies.clear()
        adapter.movies.addAll(movies)
        
        adapter.notifyDataSetChanged()
    }
    
    private fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> showLoading()
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Content -> {
                showContent(state.listFilms)
            }
        }
    }
    
    private fun showToastMessage(text: String) {
        Toast
            .makeText(requireContext(), text, Toast.LENGTH_LONG)
            .show()
    }
    
    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
    
    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}