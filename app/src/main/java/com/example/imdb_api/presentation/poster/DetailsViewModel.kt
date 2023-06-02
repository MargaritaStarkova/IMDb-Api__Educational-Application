package com.example.imdb_api.presentation.poster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.domain.models.MovieDetails
import com.example.imdb_api.ui.models.DetailsState

class DetailsViewModel(
    movieId: String,
    moviesInteractor: MoviesInteractor,
) : ViewModel() {
    
    private val stateLiveData = MutableLiveData<DetailsState>()
    fun observeState(): LiveData<DetailsState> = stateLiveData
    
    init {
        moviesInteractor.getDataFromApi(movieId, object : MoviesInteractor.Consumer {
            override fun <T> consume(data: T?, errorMessage: String?) {
                if (data != null) {
                    stateLiveData.postValue(DetailsState.Content(data as MovieDetails))
                } else {
                    stateLiveData.postValue(DetailsState.Error(errorMessage ?: "Unknown error"))
                }
            }
            
        })
    }
}