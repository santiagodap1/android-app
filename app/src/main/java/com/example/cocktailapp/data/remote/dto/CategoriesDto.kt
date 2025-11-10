package com.example.cocktailapp.data.remote.dto

data class CategoriesDto(
    val drinks: List<CategoryDto>
)

data class CategoryDto(
    val strCategory: String
)