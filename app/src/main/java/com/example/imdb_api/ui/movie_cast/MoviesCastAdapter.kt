package com.example.imdb_api.ui.movie_cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdb_api.R
import com.example.imdb_api.databinding.ListItemCastBinding
import com.example.imdb_api.databinding.ListItemHeaderBinding

class MoviesCastAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    
    var items = emptyList<MoviesCastRVItem>()
    
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is MoviesCastRVItem.HeaderItem -> R.layout.list_item_header
            is MoviesCastRVItem.PersonItem -> R.layout.list_item_cast
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.list_item_header -> {
                MovieCastHeaderViewHolder(
                    ListItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            
            R.layout.list_item_cast -> MovieCastViewHolder(
                ListItemCastBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            
            else -> error("Unknown viewType create [$viewType]")
        }
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        
        when (holder.itemViewType) {
            R.layout.list_item_header -> {
                val headerHolder = holder as MovieCastHeaderViewHolder
                headerHolder.bind(items[position] as MoviesCastRVItem.HeaderItem)
            }
            
            R.layout.list_item_cast -> {
                val itemsHolder = holder as MovieCastViewHolder
                itemsHolder.bind(items[position] as MoviesCastRVItem.PersonItem)
            }
            
            else -> error("Unknown viewType create [${holder.itemViewType}]")
        }
    }
    
    override fun getItemCount(): Int {
        return items.size
    }
}