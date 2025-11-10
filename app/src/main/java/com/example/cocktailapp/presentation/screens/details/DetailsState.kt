package com.example.cocktailapp.presentation.screens.details

import com.example.cocktailapp.domain.model.Cocktail

data class DetailsState(
    val isLoading: Boolean = false,
    val cocktail: Cocktail? = null,
    val error: String? = null
)
