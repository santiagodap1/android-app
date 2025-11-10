package com.example.cocktailapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.domain.use_case.GetCategoriesUseCase
import com.example.cocktailapp.domain.use_case.GetDrinksByCategoryUseCase
import com.example.cocktailapp.domain.use_case.GetIngredientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getDrinksByCategoryUseCase: GetDrinksByCategoryUseCase,
    private val getIngredientsUseCase: GetIngredientsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch {
            _state.value = HomeState(isLoading = true)
            try {
                val ingredientsDeferred = async { getIngredientsUseCase().shuffled().take(6) }
                val categoriesDeferred = async { getCategoriesUseCase() }

                val randomIngredients = ingredientsDeferred.await()
                val categories = categoriesDeferred.await()

                val categoriesWithDrinks = categories.map {
                    async { 
                        val drinks = getDrinksByCategoryUseCase(it.name)
                        CategoryWithDrinks(it.name, drinks)
                    }
                }.awaitAll()

                _state.value = HomeState(
                    randomIngredients = randomIngredients,
                    categoriesWithDrinks = categoriesWithDrinks
                )
            } catch (e: Exception) {
                _state.value = HomeState(error = e.message)
            }
        }
    }
}
