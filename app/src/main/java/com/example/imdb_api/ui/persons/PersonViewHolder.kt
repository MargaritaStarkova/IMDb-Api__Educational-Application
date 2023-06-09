package com.example.imdb_api.ui.persons

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imdb_api.databinding.ListItemPersonBinding
import com.example.imdb_api.domain.models.Person

class PersonViewHolder(
    private val binding: ListItemPersonBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(person: Person) {
        Glide
            .with(itemView)
            .load(person.image)
            .into(binding.valueImage)
        
        binding.nameText.text = person.name
        binding.descriptionText.text = person.description
        
    }
}