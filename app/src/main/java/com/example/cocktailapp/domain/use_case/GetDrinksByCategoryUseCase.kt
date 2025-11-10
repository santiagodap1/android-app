package com.example.cocktailapp.domain.use_case

import com.example.cocktailapp.domain.model.Cocktail
import com.example.cocktailapp.domain.repository.CocktailRepository
import javax.inject.Inject

class GetDrinksByCategoryUseCase @Inject constructor(
    private val repository: CocktailRepository
) {
    suspend operator fun invoke(category: String): List<Cocktail> {
        return repository.getDrinksByCategory(category)
    }
}