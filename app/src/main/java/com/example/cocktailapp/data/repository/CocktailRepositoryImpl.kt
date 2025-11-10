package com.example.cocktailapp.data.repository

import com.example.cocktailapp.data.remote.api.CocktailApi
import com.example.cocktailapp.data.remote.dto.toDomain
import com.example.cocktailapp.domain.model.Category
import com.example.cocktailapp.domain.model.Cocktail
import com.example.cocktailapp.domain.model.Ingredient
import com.example.cocktailapp.domain.repository.CocktailRepository
import javax.inject.Inject

class CocktailRepositoryImpl @Inject constructor(
    private val api: CocktailApi
) : CocktailRepository {

    override suspend fun getCategories(): List<Category> {
        return api.getCategories().drinks.map { it.toDomain() }
    }

    override suspend fun getDrinksByCategory(category: String): List<Cocktail> {
        return api.getDrinksByCategory(category).drinks.map { it.toDomain() }
    }

    override suspend fun getIngredients(): List<Ingredient> {
        return api.getIngredients().drinks.map { it.toDomain() }
    }

    override suspend fun getDrinksByIngredient(ingredient: String): List<Cocktail> {
        return api.getDrinksByIngredient(ingredient).drinks.map { it.toDomain() }
    }

    override suspend fun getDrinkDetails(id: String): Cocktail? {
        return api.getDrinkDetails(id).drinks.firstOrNull()?.toDomain()
    }

    override suspend fun searchDrinksByName(query: String): List<Cocktail> {
        return api.searchDrinksByName(query).drinks?.map { it.toDomain() } ?: emptyList()
    }
}