package com.example.recipefinder.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.example.recipefinder.ui.components.LoadingComponent
import com.example.recipefinder.ui.components.SuccessComponent
import com.example.recipefinder.ui.components.ErrorComponent
import com.example.recipefinder.ui.viewmodel.RecipeViewIntent
import com.example.recipefinder.ui.viewmodel.RecipeViewModel
import com.example.recipefinder.ui.viewmodel.RecipeViewState

@Composable
fun HomeScreen(recipeViewModel: RecipeViewModel) {
    val state by recipeViewModel.state

    when(state) {
        is RecipeViewState.Loading -> LoadingComponent()
        is RecipeViewState.Success -> {
            val recipes = (state as RecipeViewState.Success).recipes
            SuccessComponent(recipes = recipes, onSearchClicked = {query ->
                recipeViewModel.processIntent(RecipeViewIntent.SearchRecipes(query))
            })
        }
        is RecipeViewState.Error -> {
            val message = (state as RecipeViewState.Error).message
            ErrorComponent(message = message, onRefreshClicked = {
                recipeViewModel.processIntent(RecipeViewIntent.LoadRandomRecipe)
            })
        }
    }

    LaunchedEffect(Unit) {
        recipeViewModel.processIntent(RecipeViewIntent.LoadRandomRecipe)
    }
}