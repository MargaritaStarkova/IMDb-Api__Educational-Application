package com.example.imdb_api.presentation.history

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdb_api.R
import com.example.imdb_api.domain.db.HistoryInteractor
import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.ui.models.HistoryState
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val context: Context,
    private val historyInteractor: HistoryInteractor,
) : ViewModel() {
    
    private val stateLiveData = MutableLiveData<HistoryState>()
    
    fun observeState(): LiveData<HistoryState> = stateLiveData
    
    fun fillData() {
        renderState(HistoryState.Loading)
        viewModelScope.launch {
            historyInteractor
                .historyMovies()
                .collect { movies ->
                    processResult(movies)
                }
        }
    }
    
    private fun processResult(movies: List<Movie>) {
        if (movies.isEmpty()) {
            renderState(HistoryState.Empty(context.getString(R.string.nothing_found)))
        } else {
            renderState(HistoryState.Content(movies))
        }
    }
    
    private fun renderState(state: HistoryState) {
        stateLiveData.postValue(state)
    }
}