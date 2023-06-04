package com.example.imdb_api.presentation.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imdb_api.data.dto.cast.MoviesFullCastResponse
import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.domain.models.MovieCast
import com.example.imdb_api.domain.models.SearchType
import com.example.imdb_api.ui.models.CastState

class CastViewModel(
    movieId: String,
    moviesInteractor: MoviesInteractor,
) : ViewModel() {
    
    private val stateLiveData = MutableLiveData<CastState>()
    fun observeState(): LiveData<CastState> = stateLiveData
    
    init {
        stateLiveData.value = CastState.Loading
        moviesInteractor.getDataFromApi(
            expression = movieId,
            type = SearchType.CAST,
            consumer = object : MoviesInteractor.Consumer {
            override fun <T> consume(data: T?, errorMessage: String?) {
                if (data != null) {
                    stateLiveData.postValue(CastState.Content(data as MovieCast))
                } else {
                    stateLiveData.postValue(CastState.Error(errorMessage ?: "Unknown error"))
                }
            }
            
        })
    }
}
