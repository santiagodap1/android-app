package com.example.cocktailapp.data.remote.dto

import com.example.cocktailapp.domain.model.Category
import com.example.cocktailapp.domain.model.Cocktail
import com.example.cocktailapp.domain.model.Ingredient
import com.example.cocktailapp.domain.model.IngredientItem

fun CategoryDto.toDomain(): Category {
    return Category(name = strCategory)
}

fun CocktailDto.toDomain(): Cocktail {
    val ingredients = mutableListOf<IngredientItem>()
    strIngredient1?.let { ingredients.add(IngredientItem(it, strMeasure1.orEmpty())) }
    strIngredient2?.let { ingredients.add(IngredientItem(it, strMeasure2.orEmpty())) }
    strIngredient3?.let { ingredients.add(IngredientItem(it, strMeasure3.orEmpty())) }
    strIngredient4?.let { ingredients.add(IngredientItem(it, strMeasure4.orEmpty())) }
    strIngredient5?.let { ingredients.add(IngredientItem(it, strMeasure5.orEmpty())) }
    strIngredient6?.let { ingredients.add(IngredientItem(it, strMeasure6.orEmpty())) }
    strIngredient7?.let { ingredients.add(IngredientItem(it, strMeasure7.orEmpty())) }
    strIngredient8?.let { ingredients.add(IngredientItem(it, strMeasure8.orEmpty())) }
    strIngredient9?.let { ingredients.add(IngredientItem(it, strMeasure9.orEmpty())) }
    strIngredient10?.let { ingredients.add(IngredientItem(it, strMeasure10.orEmpty())) }
    strIngredient11?.let { ingredients.add(IngredientItem(it, strMeasure11.orEmpty())) }
    strIngredient12?.let { ingredients.add(IngredientItem(it, strMeasure12.orEmpty())) }
    strIngredient13?.let { ingredients.add(IngredientItem(it, strMeasure13.orEmpty())) }
    strIngredient14?.let { ingredients.add(IngredientItem(it, strMeasure14.orEmpty())) }
    strIngredient15?.let { ingredients.add(IngredientItem(it, strMeasure15.orEmpty())) }

    return Cocktail(
        id = idDrink,
        name = strDrink,
        image = strDrinkThumb,
        instructions = strInstructions,
        glass = strGlass,
        category = strCategory,
        ingredients = ingredients.filter { it.name.isNotBlank() }
    )
}

fun IngredientDto.toDomain(): Ingredient {
    return Ingredient(name = strIngredient1)
}
