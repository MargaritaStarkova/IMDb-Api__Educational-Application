package com.example.imdb_api.ui.models

sealed interface ToastState {
    object None: ToastState
    data class Show(val additionalMessage: String): ToastState
}