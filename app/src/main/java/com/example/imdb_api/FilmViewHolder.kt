package com.example.imdb_api

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FilmViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

    val image = parent.findViewById<ImageView>(R.id.poster)

    fun bind(film: FilmData) {

        Glide.with(itemView.context)
            .load(film.image)
            .into(image)

    }
}