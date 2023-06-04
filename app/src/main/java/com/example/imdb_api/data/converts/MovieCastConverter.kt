package com.example.imdb_api.data.converts

import com.example.imdb_api.data.dto.cast.Actor
import com.example.imdb_api.data.dto.cast.Directors
import com.example.imdb_api.data.dto.cast.Item
import com.example.imdb_api.data.dto.cast.MoviesFullCastResponse
import com.example.imdb_api.data.dto.cast.Other
import com.example.imdb_api.data.dto.cast.Writers
import com.example.imdb_api.domain.models.MovieCast
import com.example.imdb_api.domain.models.MovieCastPerson

class MovieCastConverter {
    
    fun convert(response: MoviesFullCastResponse): MovieCast {
        return with(response) {
            MovieCast(
                imdbId = this.imDbId,
                fullTitle = this.fullTitle,
                directors = convertDirectors(this.directors),
                others = convertOthers(this.others),
                writers = convertWriters(this.writers),
                actors = convertActors(this.actors)
            )
        }
    }
    
    private fun convertDirectors(directorsResponse: Directors): List<MovieCastPerson> {
        return directorsResponse.items.map { it.toMovieCastPerson() }
    }
    
    private fun convertOthers(othersResponses: List<Other>): List<MovieCastPerson> {
        return othersResponses.flatMap { otherResponse ->
            otherResponse.items.map { it.toMovieCastPerson(jobPrefix = otherResponse.job) }
        }
    }
    
    private fun convertWriters(writersResponse: Writers): List<MovieCastPerson> {
        return writersResponse.items.map { it.toMovieCastPerson() }
    }
    
    private fun convertActors(actorsResponses: List<Actor>): List<MovieCastPerson> {
        return actorsResponses.map { actor ->
            MovieCastPerson(
                id = actor.id,
                name = actor.name,
                description = actor.asCharacter,
                image = actor.image,
            )
        }
    }
    
    private fun Item.toMovieCastPerson(jobPrefix: String = ""): MovieCastPerson {
        return MovieCastPerson(
            id = this.id,
            name = this.name,
            description = if (jobPrefix.isEmpty()) this.description else "$jobPrefix -- ${this.description}",
            image = null,
        )
    }
    
}