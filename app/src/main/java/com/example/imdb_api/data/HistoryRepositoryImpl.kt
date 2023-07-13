package com.example.imdb_api.data

import com.example.imdb_api.data.converts.MovieDbConvertor
import com.example.imdb_api.data.db.AppDatabase
import com.example.imdb_api.data.db.entity.MovieEntity
import com.example.imdb_api.domain.db.HistoryRepository
import com.example.imdb_api.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val movieDbConvertor: MovieDbConvertor,
) : HistoryRepository {
    override fun historyMovies(): Flow<List<Movie>> = flow {
        val movies = appDatabase.movieDao().getMovies()
        emit(convertFromMovieEntity(movies))
    }
    
    private fun convertFromMovieEntity(movies: List<MovieEntity>): List<Movie> {
        return movies.map { movie -> movieDbConvertor.map(movie, false) }
    }
}