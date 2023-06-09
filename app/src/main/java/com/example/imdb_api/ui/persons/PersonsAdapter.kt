package com.example.imdb_api.ui.persons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdb_api.databinding.ListItemPersonBinding
import com.example.imdb_api.domain.models.Person

class PersonsAdapter : RecyclerView.Adapter<PersonViewHolder>() {
    
    val persons = ArrayList<Person>()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder =
        PersonViewHolder(
            ListItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    
    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(persons[position])
    }
    
    override fun getItemCount(): Int = persons.size
}
