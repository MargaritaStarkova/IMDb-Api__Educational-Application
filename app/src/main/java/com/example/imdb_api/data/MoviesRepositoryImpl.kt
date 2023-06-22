package com.example.imdb_api.data

import com.example.imdb_api.core.util.Resource
import com.example.imdb_api.data.converts.MovieCastConverter
import com.example.imdb_api.data.dto.cast.MoviesFullCastResponse
import com.example.imdb_api.data.dto.details.MovieDetailsResponse
import com.example.imdb_api.data.dto.movies.MoviesSearchResponse
import com.example.imdb_api.data.dto.persons.PersonsSearchResponse
import com.example.imdb_api.data.storage.LocalStorage
import com.example.imdb_api.domain.api.MoviesRepository
import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.domain.models.MovieDetails
import com.example.imdb_api.domain.models.Person
import com.example.imdb_api.domain.models.SearchRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MovieCastConverter,
) : MoviesRepository {
    
    override fun addMoviesToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }
    
    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }
    
    override fun <T> search(request: SearchRequest): Flow<Resource<T>> = flow {
        val response = networkClient.doRequest(request)
        if (response == null) emit(Resource.Error("Ничего не нашлось", null))
        
        when (response?.resultCode) {
            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))
            200 -> {
                when (request) {
                    is SearchRequest.MovieCastRequest -> {
                        
                        response as MoviesFullCastResponse
                        @Suppress("UNCHECKED_CAST") emit(
                            Resource.Success(
                                data = movieCastConverter.convert(
                                    response
                                )
                            ) as Resource<T>
                        )
                    }
                    
                    is SearchRequest.MovieDetailsRequest -> {
                        
                        response as MovieDetailsResponse
                        @Suppress("UNCHECKED_CAST") with(response) {
                            emit(
                                Resource.Success(
                                    MovieDetails(
                                        id,
                                        title,
                                        imDbRating,
                                        year,
                                        countries,
                                        genres,
                                        directors,
                                        writers,
                                        stars,
                                        plot
                                    )
                                ) as Resource<T>
                            )
                        }
                        
                    }
                    
                    is SearchRequest.MoviesSearchRequest -> {
                        val stored = localStorage.getSavedFavorites()
                        val resultList = (response as MoviesSearchResponse).results
                        
                        if (resultList.isNullOrEmpty()) {
                            emit(Resource.Error("Ничего не нашлось", null))
                            
                        } else {
                            @Suppress("UNCHECKED_CAST") emit(Resource.Success(resultList.map {
                                Movie(
                                    id = it.id,
                                    resultType = it.resultType,
                                    image = it.image,
                                    title = it.title,
                                    description = it.description,
                                    inFavorite = stored.contains(it.id)
                                )
                                
                            }) as Resource<T>)
                        }
                    }
                    
                    is SearchRequest.PersonsSearchRequest -> {
                        val resultList = (response as PersonsSearchResponse).results
                        
                        if (resultList.isEmpty()) {
                            emit(Resource.Error("Ничего не нашлось", null))
                            
                        } else {
                            
                            @Suppress("UNCHECKED_CAST") emit(Resource.Success(resultList.map {
                                Person(
                                    name = it.title,
                                    image = it.image,
                                    description = it.description,
                                )
                                
                            }) as Resource<T>)
                        }
                        
                    }
                }
            }
            
            else -> emit(Resource.Error("Ошибка сервера", null))
        }
    }
}


