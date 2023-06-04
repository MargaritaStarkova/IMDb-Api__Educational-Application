package com.example.imdb_api.presentation.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imdb_api.data.dto.cast.MoviesFullCastResponse
import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.domain.models.MovieCast
import com.example.imdb_api.domain.models.SearchType
import com.example.imdb_api.ui.models.CastState
import com.example.imdb_api.ui.models.MoviesState
import com.example.imdb_api.ui.movie_cast.MoviesCastRVItem

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
                    stateLiveData.postValue(castToUiStateContent(data as MovieCast))
                } else {
                    stateLiveData.postValue(CastState.Error(errorMessage ?: "Unknown error"))
                }
            }
            
        })
    }
    
    private fun castToUiStateContent(cast: MovieCast): CastState {
        val items = buildList<MoviesCastRVItem> {
            // Если есть хотя бы один режиссёр, добавим заголовок
            if (cast.directors.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Directors")
                this += cast.directors.map { MoviesCastRVItem.PersonItem(it) }
            }
            // Если есть хотя бы один сценарист, добавим заголовок
            if (cast.writers.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Writers")
                this += cast.writers.map { MoviesCastRVItem.PersonItem(it) }
            }
            // Если есть хотя бы один актёр, добавим заголовок
            if (cast.actors.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Actors")
                this += cast.actors.map { MoviesCastRVItem.PersonItem(it) }
            }
            // Если есть хотя бы один дополнительный участник, добавим заголовок
            if (cast.others.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Others")
                this += cast.others.map { MoviesCastRVItem.PersonItem(it) }
            }
        }
        return CastState.Content(
            fullTitle = cast.fullTitle,
            items = items
        )
    }
}
