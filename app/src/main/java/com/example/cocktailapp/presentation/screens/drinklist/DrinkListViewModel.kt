package com.example.cocktailapp.presentation.screens.drinklist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.domain.use_case.GetDrinksByIngredientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkListViewModel @Inject constructor(
    private val getDrinksByIngredientUseCase: GetDrinksByIngredientUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DrinkListState())
    val state: StateFlow<DrinkListState> = _state.asStateFlow()
    
    var ingredientName by mutableStateOf("")
        private set

    init {
        savedStateHandle.get<String>("ingredientName")?.let {
            ingredientName ->
            this.ingredientName = ingredientName
            getDrinksByIngredient(ingredientName)
        }
    }

    private fun getDrinksByIngredient(ingredientName: String) {
        viewModelScope.launch {
            _state.value = DrinkListState(isLoading = true)
            try {
                val drinks = getDrinksByIngredientUseCase(ingredientName)
                _state.value = DrinkListState(drinks = drinks)
            } catch (e: Exception) {
                _state.value = DrinkListState(error = e.message)
            }
        }
    }
}