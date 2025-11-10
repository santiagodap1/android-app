package com.example.cocktailapp.domain.use_case

import com.example.cocktailapp.domain.model.Category
import com.example.cocktailapp.domain.repository.CocktailRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CocktailRepository
) {
    suspend operator fun invoke(): List<Category> {
        return repository.getCategories()
    }
}