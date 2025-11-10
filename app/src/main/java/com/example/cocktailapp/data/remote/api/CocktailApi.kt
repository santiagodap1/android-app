package com.example.cocktailapp.data.remote.api

import com.example.cocktailapp.data.remote.dto.CategoriesDto
import com.example.cocktailapp.data.remote.dto.DrinksDto
import com.example.cocktailapp.data.remote.dto.IngredientsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("list.php?c=list")
    suspend fun getCategories(): CategoriesDto

    @GET("filter.php")
    suspend fun getDrinksByCategory(@Query("c") category: String): DrinksDto

    @GET("list.php?i=list")
    suspend fun getIngredients(): IngredientsDto

    @GET("filter.php")
    suspend fun getDrinksByIngredient(@Query("i") ingredient: String): DrinksDto

    @GET("lookup.php")
    suspend fun getDrinkDetails(@Query("i") id: String): DrinksDto

    @GET("search.php")
    suspend fun searchDrinksByName(@Query("s") query: String): DrinksDto

}