package com.example.recipefinder.ui.viewmodel

import com.example.recipefinder.data.model.Meal

sealed class RecipeViewState{
    object Loading: RecipeViewState()
    data class Success(val recipes: List<Meal>): RecipeViewState()
    data class Error(val message: String): RecipeViewState()
}
