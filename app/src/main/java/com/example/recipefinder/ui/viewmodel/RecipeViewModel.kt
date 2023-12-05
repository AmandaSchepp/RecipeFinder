package com.example.recipefinder.ui.viewmodel

import com.example.recipefinder.data.network.MealApiClient

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// ViewModel class responsible for managing the state related to recipe data
class RecipeViewModel : ViewModel() {

    // Mutable state to hold the current view state, initialized as Loading
    private val _state = mutableStateOf<RecipeViewState>(RecipeViewState.Loading)

    // Publicly exposed read-only state for observing changes in the view state
    val state: State<RecipeViewState> = _state

    // Function to process different RecipeViewIntents and trigger corresponding actions
    fun processIntent(intent: RecipeViewIntent) {
        when (intent) {
            is RecipeViewIntent.LoadRandomRecipe -> loadRandomRecipe()
            is RecipeViewIntent.SearchRecipes -> searchRecipe(intent.query)
        }
    }

    // Coroutine function to load a random recipe and update the view state
    private fun loadRandomRecipe() {
        viewModelScope.launch {
            // Set the view state to Loading before making the API call
            _state.value = RecipeViewState.Loading
            try {
                // Try to fetch a random recipe using the MealApiClient
                _state.value = RecipeViewState.Success(MealApiClient.getRandomRecipe())
            } catch (e: Exception) {
                // Handle errors by updating the view state with an error message
                _state.value = RecipeViewState.Error("Error fetching recipe")
            }
        }
    }

    // Coroutine function to search for recipes based on a query and update the view state
    private fun searchRecipe(query: String) {
        viewModelScope.launch {
            // Set the view state to Loading before making the API call
            _state.value = RecipeViewState.Loading
            try {
                // Try to fetch recipes based on the provided query using the MealApiClient
                _state.value = RecipeViewState.Success(MealApiClient.getSearchedRecipe(query))
            } catch (e: Exception) {
                // Handle errors by updating the view state with an error message
                _state.value = RecipeViewState.Error("Error fetching recipes")
            }
        }
    }
}