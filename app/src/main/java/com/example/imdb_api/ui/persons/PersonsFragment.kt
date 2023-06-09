package com.example.imdb_api.ui.persons

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdb_api.databinding.FragmentPersonsBinding
import com.example.imdb_api.domain.models.Person
import com.example.imdb_api.presentation.persons.PersonsViewModel
import com.example.imdb_api.ui.models.PersonsState
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonsFragment : Fragment() {
    
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentPersonsBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModel<PersonsViewModel>()
    private var textWatcher: TextWatcher? = null
    
    private val adapter = PersonsAdapter()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel
            .observeState()
            .observe(viewLifecycleOwner) { personsState ->
                render(personsState)
            }
        viewModel
            .observeShowToast()
            .observe(viewLifecycleOwner) { message ->
                showToastMessage(message)
            }
        
        binding.personsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.personsList.adapter = adapter
        
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }
            
            override fun afterTextChanged(s: Editable?) {}
        }
        textWatcher.let { binding.queryInput.addTextChangedListener(it) }
        
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        
        //удаляем listener
        textWatcher?.let { binding.queryInput.removeTextChangedListener(it) }
    }
    
    private fun showLoading() {
        binding.apply {
            placeholderMessage.visibility = View.GONE
            personsList.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }
    
    private fun showError(errorMessage: String) {
        binding.apply {
            placeholderMessage.visibility = View.VISIBLE
            personsList.visibility = View.GONE
            progressBar.visibility = View.GONE
            
            placeholderMessage.text = errorMessage
        }
    }
    
    private fun showContent(movies: List<Person>) {
        
        binding.apply {
            placeholderMessage.visibility = View.GONE
            personsList.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
        
        adapter.persons.clear()
        adapter.persons.addAll(movies)
        
        adapter.notifyDataSetChanged()
    }
    
    private fun render(state: PersonsState) {
        when (state) {
            is PersonsState.Loading -> showLoading()
            is PersonsState.Error -> showError(state.errorMessage)
            is PersonsState.Content -> {
                showContent(state.list)
            }
        }
    }
    
    private fun showToastMessage(text: String) {
        Toast
            .makeText(requireContext(), text, Toast.LENGTH_LONG)
            .show()
    }
    
    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
