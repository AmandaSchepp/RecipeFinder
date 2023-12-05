package com.example.recipefinder.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// Composable function representing an error component with a message and a refresh button
@Composable
fun ErrorComponent(message: String, onRefreshClicked: () -> Unit) {
    // Column layout to vertically arrange the content
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the error message
        Text(text = message)

        // Button to trigger a refresh action
        Button(onClick = onRefreshClicked) {
            Text(text = "Refresh")
        }
    }
}