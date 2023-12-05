package com.example.recipefinder.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipefinder.data.model.Meal

// Composable function representing a recipe item in a list
@Composable
fun RecipeListItem(meal: Meal) {
    // State to track the expanded/collapsed state of the instructions section
    var expanded by remember { mutableStateOf(false) }

    // Card to display the recipe details
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            Color(0xFFd7edc7) // Assigns a light green color to the card.
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // Display the thumbnail image if available
            if (!meal.strMealThumb.isNullOrEmpty()) {
                AsyncImage(
                    model = meal.strMealThumb,
                    contentDescription = "thumbnail",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            // Display the recipe name
            Text(
                text = meal.strMeal ?: "",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(8.dp))
            // Display the heading for ingredients
            Text(
                text = "Ingredients",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            // Display the list of ingredients and their measures
            Text(
                text = getIngredients(meal)
            )
            Spacer(modifier = Modifier.padding(8.dp))

            // AnimatedVisibility for the instructions section
            AnimatedVisibility(visible = expanded) {
                // Display the heading for instructions
                Column {
                    Text(
                        text = "Instructions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    // Display the actual instructions
                    Text(
                        text = meal.strInstructions ?: ""
                    )
                }
            }
            // Clickable area to toggle the visibility of instructions
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = !expanded
                    }) {
                // Icon indicating the dropdown arrow for instructions
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Clear",
                    tint = Color.Black,
                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                )
            }
        }
    }
}

// Function to format and concatenate ingredients and their measures
fun getIngredients(meal: Meal): String {
    var ingredients = ""

    with(meal) {
        ingredients += formatIngredient(strIngredient1, strMeasure1)
        ingredients += formatIngredient(strIngredient2, strMeasure2)
        ingredients += formatIngredient(strIngredient3, strMeasure3)
        ingredients += formatIngredient(strIngredient4, strMeasure4)
        ingredients += formatIngredient(strIngredient5, strMeasure5)
        ingredients += formatIngredient(strIngredient6, strMeasure6)
        ingredients += formatIngredient(strIngredient7, strMeasure7)
        ingredients += formatIngredient(strIngredient8, strMeasure8)
        ingredients += formatIngredient(strIngredient9, strMeasure9)
        ingredients += formatIngredient(strIngredient10, strMeasure10)
        ingredients += formatIngredient(strIngredient11, strMeasure11)
        ingredients += formatIngredient(strIngredient12, strMeasure12)
        ingredients += formatIngredient(strIngredient13, strMeasure13)
        ingredients += formatIngredient(strIngredient14, strMeasure14)
        ingredients += formatIngredient(strIngredient15, strMeasure15)
        ingredients += formatIngredient(strIngredient16, strMeasure16)
        ingredients += formatIngredient(strIngredient17, strMeasure17)
        ingredients += formatIngredient(strIngredient18, strMeasure18)
        ingredients += formatIngredient(strIngredient19, strMeasure19)
        ingredients += formatIngredient(strIngredient20, strMeasure20)
    }
    return ingredients.trimEnd('\n')
}

// Function to format a single ingredient and its measure
fun formatIngredient(ingredient: String?, measure: String?): String {
    return if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
        "$ingredient - $measure\n"
    } else {
        ""
    }
}