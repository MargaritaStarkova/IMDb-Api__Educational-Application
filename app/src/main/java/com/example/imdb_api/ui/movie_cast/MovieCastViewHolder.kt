package com.example.imdb_api.ui.movie_cast

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imdb_api.databinding.ListItemCastBinding
import com.example.imdb_api.domain.models.MovieCastPerson

class MovieCastViewHolder(private val binding: ListItemCastBinding) :
    RecyclerView.ViewHolder(binding.root) {
    
    fun bind(item: MoviesCastRVItem.PersonItem) {
        if (item.data.image == null) {
            binding.imageView.isVisible = false
        } else {
            Glide
                .with(itemView)
                .load(item.data.image)
                .into(binding.imageView)
            
            binding.imageView.isVisible = true
        }
        
        binding.nameText.text = item.data.name
        binding.descriptionText.text = item.data.description
        
    }
}
