package com.example.imdb_api.presentation.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imdb_api.data.dto.cast.MovieCastResponse
import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.ui.models.CastState

class CastViewModel(
    movieId: String,
    moviesInteractor: MoviesInteractor,
) : ViewModel() {
    
    private val stateLiveData = MutableLiveData<CastState>()
    fun observeState(): LiveData<CastState> = stateLiveData
    
    init {
        moviesInteractor.getDataFromApi(movieId, object : MoviesInteractor.Consumer {
            override fun <T> consume(data: T?, errorMessage: String?) {
                if (data != null) {
                    stateLiveData.postValue(CastState.Content(data as MovieCastResponse))
                } else {
                    stateLiveData.postValue(CastState.Error(errorMessage ?: "Unknown error"))
                }
            }
            
        })
    }
}
