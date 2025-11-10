package com.example.cocktailapp.domain.use_case

import com.example.cocktailapp.domain.model.Ingredient
import com.example.cocktailapp.domain.repository.CocktailRepository
import javax.inject.Inject

class GetIngredientsUseCase @Inject constructor(
    private val repository: CocktailRepository
) {
    suspend operator fun invoke(): List<Ingredient> {
        return repository.getIngredients()
    }
}