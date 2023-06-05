package com.example.imdb_api.ui.movie_cast

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.imdb_api.databinding.ListItemCastBinding
import com.example.imdb_api.databinding.ListItemHeaderBinding
import com.example.imdb_api.core.root.RVItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

// Делегат для заголовков на экране состава участников
fun movieCastHeaderDelegate() =
    adapterDelegateViewBinding<MoviesCastRVItem.HeaderItem, RVItem, ListItemHeaderBinding>({ layoutInflater, root ->
        ListItemHeaderBinding.inflate(
            layoutInflater,
            root,
            false
        )
    }) {
        bind {
            binding.headerTextView.text = item.headerText
        }
    }
// Делегат для отображения участников на соответствующем экране
fun movieCastPersonDelegate() = adapterDelegateViewBinding<MoviesCastRVItem.PersonItem, RVItem, ListItemCastBinding>( { layoutInflater, root ->
    ListItemCastBinding.inflate(
        layoutInflater,
        root,
        false
    )
}) {
    bind {
        if (item.data.image == null) {
            binding.imageView.isVisible = false
        } else {
            Glide.with(itemView)
                .load(item.data.image)
                .into(binding.imageView)
            binding.imageView.isVisible = true
        }
        binding.nameText.text = item.data.name
        binding.descriptionText.text = item.data.description
    }
}