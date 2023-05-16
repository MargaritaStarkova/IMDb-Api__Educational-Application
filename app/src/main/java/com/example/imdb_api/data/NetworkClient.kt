package com.example.imdb_api.data

import com.example.imdb_api.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}