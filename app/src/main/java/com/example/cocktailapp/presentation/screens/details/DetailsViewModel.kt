package com.example.cocktailapp.presentation.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.domain.use_case.GetDrinkDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDrinkDetailsUseCase: GetDrinkDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("drinkId")?.let {
            drinkId ->
            getDrinkDetails(drinkId)
        }
    }

    private fun getDrinkDetails(id: String) {
        viewModelScope.launch {
            _state.value = DetailsState(isLoading = true)
            try {
                val cocktail = getDrinkDetailsUseCase(id)
                _state.value = DetailsState(cocktail = cocktail)
            } catch (e: Exception) {
                _state.value = DetailsState(error = e.message)
            }
        }
    }
}