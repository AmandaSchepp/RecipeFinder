package com.example.recipefinder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.recipefinder.sign_in.UserData

@Composable
fun HamburgerMenuComponent(
    expanded: MutableState<Boolean>,
    userData: UserData?,
    onSignOut: () -> Unit
) {
    // Hamburger menu icon
    IconButton(onClick = { expanded.value = !expanded.value }) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
    }

    // Hamburger menu content
    if (expanded.value) {
        Box(
            modifier = Modifier
                .padding(8.dp) // Adjust padding as needed
                .background(colorScheme.surface)
        ) {
            // Sign Out button in the menu
            if (userData != null) {
                Box(
                    modifier = Modifier
                        .padding(8.dp) // Adjust padding as needed
                        .clickable {
                            expanded.value = false
                            onSignOut()
                        }
                ) {
                    Text("Sign Out")
                }
            }
        }
    }
}