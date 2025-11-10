package com.example.cocktailapp.presentation.screens.drinklist

import com.example.cocktailapp.domain.model.Cocktail

data class DrinkListState(
    val isLoading: Boolean = false,
    val drinks: List<Cocktail> = emptyList(),
    val error: String? = null
)
