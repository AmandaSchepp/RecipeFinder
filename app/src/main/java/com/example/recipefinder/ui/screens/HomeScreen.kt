package com.example.recipefinder.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.recipefinder.sign_in.UserData
import com.example.recipefinder.ui.components.LoadingComponent
import com.example.recipefinder.ui.components.SuccessComponent
import com.example.recipefinder.ui.components.ErrorComponent
import com.example.recipefinder.ui.viewmodel.RecipeViewIntent
import com.example.recipefinder.ui.viewmodel.RecipeViewModel
import com.example.recipefinder.ui.viewmodel.RecipeViewState

// Composable function representing the home screen
@Composable
fun HomeScreen(
    recipeViewModel: RecipeViewModel,
    userData: UserData?,
    onSignOut: () -> Unit,
    navController: NavController
) {
    // Retrieve the current state from the RecipeViewModel
    val state by recipeViewModel.state

    // State to manage the expansion state of the hamburger menu
    val expandedState = remember { mutableStateOf(false) }

    // Callback function for refreshing the screen
    val onRefreshClicked: () -> Unit = {
        recipeViewModel.processIntent(RecipeViewIntent.LoadRandomRecipe)
    }

    // Render the appropriate content based on the state
    when (state) {
        is RecipeViewState.Loading -> LoadingComponent()
        is RecipeViewState.Success -> {
            val recipes = (state as RecipeViewState.Success).recipes
            Column {
                ///HamburgerMenuComponent(expandedState, userData, onSignOut)
                // Display the SuccessComponent with the list of recipes
                SuccessComponent(
                    recipes = recipes,
                    onSearchClicked = { query ->
                        recipeViewModel.processIntent(RecipeViewIntent.SearchRecipes(query))
                    },
                    expanded = expandedState, // Pass the 'expandedState' as an argument
                    userData = userData,
                    onSignOut = onSignOut,
                    onNotesClicked = {
                        // Handle navigation to the "notes" destination
                        navController.navigate("notes")
                    },
                    onTimerClicked = {
                        // Handle navigation to the "timer" destination
                        navController.navigate("timer")
                    },
                    onRefreshClicked = onRefreshClicked
                )
            }
        }
        is RecipeViewState.Error -> {
            val message = (state as RecipeViewState.Error).message
            // Display the ErrorComponent with the error message
            ErrorComponent(message = message, onRefreshClicked = {
                recipeViewModel.processIntent(RecipeViewIntent.LoadRandomRecipe)
            })
        }
    }

    // Initiate loading a random recipe when the screen is launched
    LaunchedEffect(Unit) {
        recipeViewModel.processIntent(RecipeViewIntent.LoadRandomRecipe)
    }
}