package com.example.imdb_api.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
class MovieEntity(
    @PrimaryKey
    val id: String,
    val resultType: String,
    val image: String,
    val title: String,
    val description: String,
)