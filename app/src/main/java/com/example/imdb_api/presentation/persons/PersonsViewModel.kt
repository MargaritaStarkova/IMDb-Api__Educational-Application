package com.example.imdb_api.presentation.persons

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.imdb_api.R
import com.example.imdb_api.core.util.SingleLiveEvent
import com.example.imdb_api.di.MoviesApplication
import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.domain.models.Person
import com.example.imdb_api.domain.models.SearchRequest
import com.example.imdb_api.ui.models.PersonsState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PersonsViewModel(
    application: MoviesApplication,
    private val interactor: MoviesInteractor,
) : AndroidViewModel(application) {
    
    private var latestSearchText: String? = null
    private var searchJob: Job? = null

    private val personList = ArrayList<Person>()
    
    //Создаем LiveData
    private val stateLiveData = MutableLiveData<PersonsState>()
    private val showToast = SingleLiveEvent<String>()
    
    
    fun observeState(): LiveData<PersonsState> = stateLiveData
    fun observeShowToast(): LiveData<String> = showToast
    
    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) return
        
        this.latestSearchText = changedText
        
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            searchRequest(changedText)
        }
        

    }
    
    private fun renderState(state: PersonsState) {
        stateLiveData.postValue(state)
    }
    
    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
    
            renderState(PersonsState.Loading)
    
            viewModelScope.launch {
                interactor
                    .getDataFromApi<List<Person>>(
                        request = SearchRequest.PersonsSearchRequest(newSearchText)
                    )
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
    
        }
    }
    
    private fun processResult(data: List<Person>?, errorMessage: String?) {
        if (data != null) {
            personList.clear()
            personList.addAll(data)
        }
        
        when {
            errorMessage != null -> {
                renderState(
                    PersonsState.Error(getApplication<Application>().getString(R.string.something_went_wrong))
                )
                
                showToast.postValue(errorMessage!!)
            }
            
            personList.isEmpty() -> renderState(
                PersonsState.Error(
                    getApplication<Application>().getString(R.string.nothing_found)
                )
            )
            
            else -> renderState(
                PersonsState.Content(personList)
            )
        }
        
    }
    
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
    
}