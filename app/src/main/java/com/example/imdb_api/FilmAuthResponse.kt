package com.example.imdb_api

import com.google.gson.annotations.SerializedName

class FilmAuthResponse (@SerializedName("access_token") val token: String)