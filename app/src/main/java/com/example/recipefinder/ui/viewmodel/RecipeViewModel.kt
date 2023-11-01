package com.example.recipefinder.ui.viewmodel
import com.example.recipefinder.data.network.MealApiClient

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel: ViewModel() {
    private val _state = mutableStateOf<RecipeViewState>(RecipeViewState.Loading)
    val state: State<RecipeViewState> = _state

    fun processIntent(intent: RecipeViewIntent) {
        when(intent) {
            is RecipeViewIntent.LoadRandomRecipe -> loadRandomRecipe()
            is RecipeViewIntent.SearchRecipes -> searchRecipe(intent.query)
        }
    }

    private fun loadRandomRecipe() {
        viewModelScope.launch {
            _state.value = RecipeViewState.Loading
            try {
                _state.value = RecipeViewState.Success(
                    MealApiClient.getRandomRecipe()
                )
            } catch(e: Exception) {
                _state.value = RecipeViewState.Error("Error fetching recipe")
            }
        }
    }

    private fun searchRecipe(query: String) {
        viewModelScope.launch {
            _state.value = RecipeViewState.Loading
            try {
                _state.value = RecipeViewState.Success(
                    MealApiClient.getSearchedRecipe(query)
                )
            } catch (e: Exception) {
                _state.value = RecipeViewState.Error("Error fetching recipes")
            }
        }
    }

}