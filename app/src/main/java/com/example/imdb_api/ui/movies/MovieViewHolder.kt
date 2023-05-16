package com.example.imdb_api.ui.movies

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imdb_api.R
import com.example.imdb_api.domain.models.Movie

class MovieViewHolder(
    parent: ViewGroup,
    private val clickListener: MoviesAdapter.MovieClickListener
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
) {

    private var cover: ImageView = itemView.findViewById(R.id.cover)
    private var title: TextView = itemView.findViewById(R.id.title)
    private var description: TextView = itemView.findViewById(R.id.description)
    private var inFavoriteToggle: ImageView = itemView.findViewById(R.id.favorite)

    fun bind(movie: Movie) {
        Glide.with(itemView).load(movie.image).into(cover)

        title.text = movie.title
        description.text = movie.description
        inFavoriteToggle.setImageDrawable(getFavoriteToggleDrawable(movie.inFavorite))

        itemView.setOnClickListener { clickListener.onMovieClick(movie) }
        inFavoriteToggle.setOnClickListener { clickListener.onFavoriteToggleClick(movie) }
    }

    private fun getFavoriteToggleDrawable(inFavorite: Boolean): Drawable? {
        return AppCompatResources.getDrawable(itemView.context,
            if (inFavorite) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
        )

    }
}