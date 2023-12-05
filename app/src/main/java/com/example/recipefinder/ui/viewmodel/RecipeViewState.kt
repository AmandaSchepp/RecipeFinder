package com.example.recipefinder.ui.viewmodel

import com.example.recipefinder.data.model.Meal

// Sealed class representing different states for recipe data in the UI
sealed class RecipeViewState {

    // Loading state indicating that data is being fetched
    object Loading : RecipeViewState()

    // Success state containing a list of recipes
    data class Success(val recipes: List<Meal>) : RecipeViewState()

    // Error state containing an error message
    data class Error(val message: String) : RecipeViewState()
}
