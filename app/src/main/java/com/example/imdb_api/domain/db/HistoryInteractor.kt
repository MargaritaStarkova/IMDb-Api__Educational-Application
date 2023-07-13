package com.example.imdb_api.domain.db

import com.example.imdb_api.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface HistoryInteractor {
    
    fun historyMovies(): Flow<List<Movie>>
}