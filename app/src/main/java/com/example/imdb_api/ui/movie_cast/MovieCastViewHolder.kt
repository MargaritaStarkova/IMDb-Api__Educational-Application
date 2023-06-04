package com.example.imdb_api.ui.movie_cast

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imdb_api.databinding.ListItemCastBinding
import com.example.imdb_api.domain.models.MovieCastPerson

class MovieCastViewHolder(private val binding: ListItemCastBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movieCastPerson: MovieCastPerson) {
        if (movieCastPerson.image == null) {
            binding.imageView.isVisible = false
        } else {
            Glide
                .with(itemView)
                .load(movieCastPerson.image)
                .into(binding.imageView)
            
            binding.imageView.isVisible = true
        }
        
        binding.nameText.text = movieCastPerson.name
        binding.descriptionText.text = movieCastPerson.description
        
    }
}
