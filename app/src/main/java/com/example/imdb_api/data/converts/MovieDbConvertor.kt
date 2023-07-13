package com.example.imdb_api.data.converts

import com.example.imdb_api.data.db.entity.MovieEntity
import com.example.imdb_api.data.dto.movies.MovieDto
import com.example.imdb_api.domain.models.Movie

class MovieDbConvertor {
    
    fun map(movie: MovieDto): MovieEntity {
        return with(movie) { MovieEntity(id, resultType, image, title, description) }
    }
    
    fun map(movie: MovieEntity, inFavorite: Boolean): Movie {
        return with(movie) { Movie(id, resultType, image, title, description, inFavorite) }
    }
}