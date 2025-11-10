package com.example.cocktailapp.presentation.screens.home

import com.example.cocktailapp.domain.model.Cocktail
import com.example.cocktailapp.domain.model.Ingredient

data class HomeState(
    val isLoading: Boolean = false,
    val randomIngredients: List<Ingredient> = emptyList(),
    val categoriesWithDrinks: List<CategoryWithDrinks> = emptyList(),
    val error: String? = null
)

data class CategoryWithDrinks(
    val category: String,
    val drinks: List<Cocktail>
)
