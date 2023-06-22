package com.example.imdb_api.data

import com.example.imdb_api.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response?
}