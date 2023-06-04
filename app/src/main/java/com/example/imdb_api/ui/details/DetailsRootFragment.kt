package com.example.imdb_api.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.imdb_api.R
import com.example.imdb_api.databinding.FragmentDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailsRootFragment : Fragment() {
    
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentDetailsBinding.inflate(
            layoutInflater
        )
    }
    
    private val tabMediator by lazy(LazyThreadSafetyMode.NONE) {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.poster)
                1 -> tab.text = getString(R.string.details)
            }
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
        
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val poster = arguments?.getString(POSTER_URL) ?: ""
        val movieId = arguments?.getString(MOVIE_ID) ?: ""
        
        binding.viewPager.adapter =
            DetailsViewPagerAdapter(parentFragmentManager, lifecycle, poster, movieId)
        
        tabMediator.attach()
        
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
    }
    
    companion object {
        
        const val TAG = "DetailsFragment"
        private const val POSTER_URL = "POSTER_URL"
        private const val MOVIE_ID = "MOVIE_ID"
        
        fun newInstance(posterUrl: String, movieId: String) = DetailsRootFragment().apply {
            arguments = bundleOf(
                POSTER_URL to posterUrl, MOVIE_ID to movieId
            )
        }
    }
}
