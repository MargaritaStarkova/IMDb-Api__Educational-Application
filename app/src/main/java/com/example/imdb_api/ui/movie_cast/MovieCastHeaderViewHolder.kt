package com.example.imdb_api.ui.movie_cast

import androidx.recyclerview.widget.RecyclerView
import com.example.imdb_api.databinding.ListItemHeaderBinding

class MovieCastHeaderViewHolder(private val binding: ListItemHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    
    fun bind(item: MoviesCastRVItem.HeaderItem) {
        binding.headerTextView.text = item.headerText
    }
}
