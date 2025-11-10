package com.example.cocktailapp.data.remote.dto

data class IngredientsDto(
    val drinks: List<IngredientDto>
)

data class IngredientDto(
    val strIngredient1: String
)
