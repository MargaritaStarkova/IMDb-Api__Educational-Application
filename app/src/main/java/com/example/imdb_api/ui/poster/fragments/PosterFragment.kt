package com.example.imdb_api.ui.poster.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.imdb_api.databinding.PosterFragmentBinding
import com.example.imdb_api.presentation.poster.PosterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PosterFragment : Fragment() {
    
    private lateinit var binding: PosterFragmentBinding
    
    private val posterViewModel: PosterViewModel by viewModel {
        parametersOf(requireArguments().getString(POSTER_URL))
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = PosterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        posterViewModel.observeUrl().observe(viewLifecycleOwner) {
            showPoster(it)
        }
    }
    
    private fun showPoster(url: String) {
        context?.let {
            Glide
                .with(it)
                .load(url)
                .into(binding.poster)
        }
    }
    
    companion object {
        private const val POSTER_URL = "POSTER_URL"
        
        fun newInstance(url: String) = PosterFragment().apply {
            arguments = Bundle().apply {
                putString(POSTER_URL, url)
            }
        }
    }
}