package com.example.recipefinder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Composable function representing a search component
@Composable
fun SearchComponent(onSearchClicked: (query: String) -> Unit) {
    // State to manage the query entered by the user
    var query by remember { mutableStateOf("") }

    // State to manage error messages related to search
    var errorMessage by remember { mutableStateOf("") }

    // Column layout to vertically arrange the content
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        // OutlinedTextField for user input
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            value = query,
            onValueChange = {
                // Clear error message if the user is entering a new query
                if (it.isNotBlank()) {
                    errorMessage = ""
                }
                query = it
            },
            label = { Text("Search") },
            singleLine = true,
            // Set isError flag based on whether there's an error message
            isError = errorMessage.isNotBlank(),
            trailingIcon = {
                // IconButton for triggering the search
                IconButton(
                    onClick = {
                        // Check if the query is not blank before triggering the search
                        if (query.isNotBlank()) {
                            onSearchClicked(query)
                        } else {
                            // Display an error message if the query is blank
                            errorMessage = "Enter a query first"
                        }
                    }
                ) {
                    // Search icon
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Black
                    )
                }
            }
        )
        // Display the error message if present
        if (errorMessage.isNotBlank()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}