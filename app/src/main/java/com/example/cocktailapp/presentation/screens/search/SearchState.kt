package com.example.cocktailapp.presentation.screens.search

import com.example.cocktailapp.domain.model.Cocktail

data class SearchState(
    val query: String = "",
    val drinks: List<Cocktail> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
