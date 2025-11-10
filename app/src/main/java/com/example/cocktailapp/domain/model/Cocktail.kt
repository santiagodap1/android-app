package com.example.cocktailapp.domain.model

data class Cocktail(
    val id: String,
    val name: String,
    val image: String,
    val instructions: String? = null,
    val glass: String? = null,
    val category: String? = null,
    val ingredients: List<IngredientItem> = emptyList()
)

data class IngredientItem(
    val name: String,
    val measure: String
)
