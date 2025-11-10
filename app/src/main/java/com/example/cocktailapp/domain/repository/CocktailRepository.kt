package com.example.cocktailapp.domain.repository

import com.example.cocktailapp.domain.model.Category
import com.example.cocktailapp.domain.model.Cocktail
import com.example.cocktailapp.domain.model.Ingredient

interface CocktailRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getDrinksByCategory(category: String): List<Cocktail>
    suspend fun getIngredients(): List<Ingredient>
    suspend fun getDrinksByIngredient(ingredient: String): List<Cocktail>
    suspend fun getDrinkDetails(id: String): Cocktail?
    suspend fun searchDrinksByName(query: String): List<Cocktail>
}