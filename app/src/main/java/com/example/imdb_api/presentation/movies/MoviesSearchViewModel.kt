package com.example.imdb_api.presentation.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.imdb_api.R
import com.example.imdb_api.core.util.SingleLiveEvent
import com.example.imdb_api.core.util.debounce
import com.example.imdb_api.di.MoviesApplication
import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.domain.models.SearchRequest
import com.example.imdb_api.ui.models.MoviesState
import kotlinx.coroutines.launch

class MoviesSearchViewModel
    (
    application: MoviesApplication,
    private val interactor: MoviesInteractor,
) : AndroidViewModel(application) {
    
    private var latestSearchText: String? = null
    
    private val movieList = ArrayList<Movie>()

    //Создаем LiveData
    private val stateLiveData = MutableLiveData<MoviesState>()
    private val showToast = SingleLiveEvent<String>()
    
    private val onMovieSearchDebounce =
        debounce<String>(
            delayMillis = SEARCH_DEBOUNCE_DELAY,
            coroutineScope = viewModelScope,
            useLastParam = true,
            action = {
                searchRequest(it)
            }
        )

    private val mediatorStateLiveData = MediatorLiveData<MoviesState>().also { liveData ->
        //1
        liveData.addSource(stateLiveData) { movieState ->
            liveData.value = when (movieState) {
                //2
                is MoviesState.Content -> MoviesState.Content(movieState.listFilms.sortedByDescending { it.inFavorite })
                is MoviesState.Error -> movieState
                is MoviesState.Loading -> movieState
            }
        }
    }


    //3
    fun observeState(): LiveData<MoviesState> = mediatorStateLiveData
    fun observeShowToast(): LiveData<String> = showToast

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) return

        this.latestSearchText = changedText
    
        onMovieSearchDebounce(changedText)

    }

    fun toggleFavorite(movie: Movie) {
        if (movie.inFavorite) {
            interactor.removeMovieFromFavorites(movie)
        } else {
            interactor.addMovieToFavorites(movie)
        }

        updateMovieContent(movie.id, movie.copy(inFavorite = !movie.inFavorite))
    }

    private fun updateMovieContent(movieId: String, newMovie: Movie) {
        val currentState = stateLiveData.value

        if (currentState is MoviesState.Content) {
            val movieIndex = currentState.listFilms.indexOfFirst { it.id == movieId }

            if (movieIndex != -1) {
                stateLiveData.value = MoviesState.Content(
                    currentState.listFilms.toMutableList().also {
                        it[movieIndex] = newMovie
                    }
                )
            }
        }

    }

    private fun renderState(state: MoviesState) {
        stateLiveData.postValue(state)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
    
            renderState(MoviesState.Loading)
            viewModelScope.launch {
                interactor
                    .getDataFromApi<List<Movie>>(
                        request = SearchRequest.MoviesSearchRequest(newSearchText)
                    )
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }
    
    private fun processResult(data: List<Movie>?, errorMessage: String?) {
        if (data != null) {
            movieList.clear()
            movieList.addAll(data)
        }
        
        when {
            errorMessage != null -> {
                renderState(
                    MoviesState.Error(getApplication<Application>().getString(R.string.something_went_wrong))
                )
                
                showToast.postValue(errorMessage!!)
            }
            
            movieList.isEmpty() -> renderState(
                MoviesState.Error(
                    getApplication<Application>().getString(R.string.nothing_found)
                )
            )
            
            else -> renderState(
                MoviesState.Content(movieList)
            )
        }
    }
    
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        
    }
}
