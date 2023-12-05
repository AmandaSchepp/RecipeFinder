package com.example.recipefinder.ui.viewmodel

// Define a sealed class for representing the possible intents for the RecipeViewModel
sealed class RecipeViewIntent {
    // Object for signaling the intent to load a random recipe
    object LoadRandomRecipe : RecipeViewIntent()

    // Data class for signaling the intent to search for recipes based on a query
    data class SearchRecipes(val query: String) : RecipeViewIntent()
}