package com.example.imdb_api.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imdb_api.R
import com.example.imdb_api.domain.models.Movie

class HistoryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.list_item_history, parent, false)
) {
    private val cover: ImageView = itemView.findViewById(R.id.cover)
    private val title: TextView = itemView.findViewById(R.id.title)
    private val description: TextView = itemView.findViewById(R.id.description)
    
    fun bind(movie: Movie) {
        Glide.with(itemView)
            .load(movie.image)
            .into(cover)
        
        title.text = movie.title
        description.text = movie.description
    }
}