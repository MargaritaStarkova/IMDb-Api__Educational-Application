package com.example.imdb_api.presentation.movies

import android.app.Application
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.imdb_api.R
import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.ui.models.MoviesState
import com.example.imdb_api.ui.models.SingleLiveEvent

class MoviesSearchViewModel
    (
    application: Application,
    private val moviesInteractor: MoviesInteractor,
) : AndroidViewModel(application) {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

    }

    private var latestSearchText: String? = null
    
    private val handler = Handler(Looper.getMainLooper())
    private val movieList = ArrayList<Movie>()

    //Создаем LiveData
    private val stateLiveData = MutableLiveData<MoviesState>()
    private val showToast = SingleLiveEvent<String>()

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

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    //3
    fun observeState(): LiveData<MoviesState> = mediatorStateLiveData
    fun observeShowToast(): LiveData<String> = showToast

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) return

        this.latestSearchText = changedText

        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { searchRequest(changedText) }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { //под капотом метода postDelayed() вызывается метод postAtTime() из ветки else
            handler.postDelayed(searchRunnable, SEARCH_REQUEST_TOKEN, SEARCH_DEBOUNCE_DELAY)
        } else {
            val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
            handler.postAtTime(searchRunnable, SEARCH_REQUEST_TOKEN, postTime)
        }
    }

    fun toggleFavorite(movie: Movie) {
        if (movie.inFavorite) {
            moviesInteractor.removeMovieFromFavorites(movie)
        } else {
            moviesInteractor.addMovieToFavorites(movie)
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

            moviesInteractor.getDataFromApi(newSearchText, object : MoviesInteractor.Consumer {
                override fun <T> consume(data: T?, errorMessage: String?) {
                    if (data != null) {
                        movieList.clear()
                        movieList.addAll(data as List<Movie>)
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
    
            })
        }
    }
}
