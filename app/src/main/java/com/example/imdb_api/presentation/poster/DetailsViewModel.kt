package com.example.imdb_api.presentation.poster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.domain.models.MovieDetails
import com.example.imdb_api.domain.models.SearchRequest
import com.example.imdb_api.ui.models.DetailsState
import kotlinx.coroutines.launch

class DetailsViewModel(
    movieId: String,
    interactor: MoviesInteractor,
) : ViewModel() {
    
    private val stateLiveData = MutableLiveData<DetailsState>()
    fun observeState(): LiveData<DetailsState> = stateLiveData
    
    init {
        viewModelScope.launch {
            interactor
                .getDataFromApi<MovieDetails>(SearchRequest.MovieDetailsRequest(movieId))
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }
    
    private fun processResult(data: MovieDetails?, errorMessage: String?) {
        if (data != null) {
            stateLiveData.postValue(DetailsState.Content(data))
        } else {
            stateLiveData.postValue(DetailsState.Error(errorMessage ?: "Unknown error"))
        }
    }
}