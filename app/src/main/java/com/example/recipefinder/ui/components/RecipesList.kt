package com.example.recipefinder.ui.components

import com.example.recipefinder.data.model.Meal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// Composable function representing a list of recipes
@Composable
fun RecipesList(recipes: List<Meal>) {
    // LazyColumn to efficiently handle a list of items
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        // Iterate through the list of recipes and display each as a RecipeListItem
        items(recipes) {
            RecipeListItem(it)
        }
    }
}