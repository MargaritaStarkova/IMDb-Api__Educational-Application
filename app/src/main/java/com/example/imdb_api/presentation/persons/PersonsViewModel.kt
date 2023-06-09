package com.example.imdb_api.presentation.persons

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imdb_api.R
import com.example.imdb_api.core.util.SingleLiveEvent
import com.example.imdb_api.di.MoviesApplication
import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.domain.models.Person
import com.example.imdb_api.domain.models.SearchType
import com.example.imdb_api.ui.models.PersonsState

class PersonsViewModel(
    application: MoviesApplication,
    private val interactor: MoviesInteractor,
) : AndroidViewModel(application) {
    
    private var latestSearchText: String? = null
    
    private val handler = Handler(Looper.getMainLooper())
    private val personList = ArrayList<Person>()
    
    //Создаем LiveData
    private val stateLiveData = MutableLiveData<PersonsState>()
    private val showToast = SingleLiveEvent<String>()
    
    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }
    
    fun observeState(): LiveData<PersonsState> = stateLiveData
    fun observeShowToast(): LiveData<String> = showToast
    
    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) return
        
        this.latestSearchText = changedText
        
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        
        val searchRunnable = Runnable { searchRequest(changedText) }
        
        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(searchRunnable, SEARCH_REQUEST_TOKEN, postTime)
    }
    
    private fun renderState(state: PersonsState) {
        stateLiveData.postValue(state)
    }
    
    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            
            renderState(PersonsState.Loading)
            
            interactor.getDataFromApi(expression = newSearchText,
                type = SearchType.PERSONS,
                consumer = object : MoviesInteractor.Consumer {
                    override fun <T> consume(data: T?, errorMessage: String?) {
                        if (data != null) {
                            personList.clear()
                            personList.addAll(data as List<Person>)
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
                })
        }
    }
    
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()
        
    }
    
}