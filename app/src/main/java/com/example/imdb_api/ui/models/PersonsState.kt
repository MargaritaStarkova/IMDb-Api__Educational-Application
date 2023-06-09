package com.example.imdb_api.ui.models

import com.example.imdb_api.domain.models.Person

sealed interface PersonsState {
    object Loading : PersonsState

    data class Content(
        val list: List<Person>
    ) : PersonsState

    data class Error(
        val errorMessage: String
    ) : PersonsState
}
