package com.example.cocktailapp.domain.use_case

import com.example.cocktailapp.domain.model.Cocktail
import com.example.cocktailapp.domain.repository.CocktailRepository
import javax.inject.Inject

class GetDrinkDetailsUseCase @Inject constructor(
    private val repository: CocktailRepository
) {
    suspend operator fun invoke(id: String): Cocktail? {
        return repository.getDrinkDetails(id)
    }
}