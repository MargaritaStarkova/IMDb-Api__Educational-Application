package com.example.imdb_api.ui.movie_cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdb_api.databinding.ListItemCastBinding
import com.example.imdb_api.domain.models.MovieCastPerson

class MoviesCastAdapter : RecyclerView.Adapter<MovieCastViewHolder>() {
    var persons = emptyList<MovieCastPerson>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder {
        return MovieCastViewHolder(ListItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        holder.bind(persons[position])
    }
    
    override fun getItemCount(): Int {
        return persons.size
    }
}