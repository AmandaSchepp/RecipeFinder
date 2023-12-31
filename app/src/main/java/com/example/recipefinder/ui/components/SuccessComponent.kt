package com.example.recipefinder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipefinder.data.model.Meal
import com.example.recipefinder.sign_in.UserData

// Composable function representing a success screen with recipes
@Composable
fun SuccessComponent(
    recipes: List<Meal>,
    onSearchClicked: (query: String) -> Unit,
    expanded: MutableState<Boolean>,
    userData: UserData?,
    onSignOut: () -> Unit,
    onNotesClicked: () -> Unit,
    onTimerClicked: () -> Unit,
    onRefreshClicked: () -> Unit
) {
    // Main Box layout to fill the entire size and set the background color
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column {
            // Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // App title
                Text(
                    text = "RecipEase",
                    fontWeight = FontWeight(900),
                    fontFamily = FontFamily.Cursive,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(
                        top = 5.dp,
                        start = 10.dp
                    )
                )

                // Display username if available
                if (userData?.username != null) {
                    Text(
                        text = userData.username,
                        textAlign = TextAlign.Right,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(
                                top = 5.dp,
                                start = 50.dp,
                                end = 10.dp
                            ),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Display profile picture if available
                if (userData?.profilePictureUrl != null) {
                    AsyncImage(
                        model = userData.profilePictureUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(
                                top = 5.dp,
                                end = 5.dp
                            )
                            .clip(CircleShape)
                    )
                }

                // Hamburger menu icon
                IconButton(onClick = { expanded.value = !expanded.value },
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
            }

            // Hamburger menu content
            if (expanded.value) {
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 5.dp) // Adjust padding as needed
                        .background(Color.White)
                ) {
                    if (userData != null) {
                        // Home menu text link
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                                .clickable {
                                    onRefreshClicked()
                                }
                        ) {
                            Text(
                                text = "\uD83D\uDD04 Refresh"
                            )
                        }
                        // Timer menu text link
                        Box(
                            modifier = Modifier
                                .padding(start = 95.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
                                .clickable {
                                    onTimerClicked()
                                }
                        ) {
                            Text(
                                text = "⏲️ Timer"
                            )
                        }
                        // Notes menu text link
                        Box(
                            modifier = Modifier
                                .padding(start = 170.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
                                .clickable {
                                    onNotesClicked()
                                }
                        ) {
                            Text(
                                text = "\uD83D\uDDD2️ Notes"
                            )
                        }
                        // Sign out menu text link
                        Box(
                            modifier = Modifier
                                .padding(start = 245.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
                                .clickable {
                                    expanded.value = false
                                    onSignOut()
                                }
                        ) {
                            Text(
                                text = "\uD83D\uDEAA Sign Out",
                                color = Color.Red
                            )
                        }
                    }
                }
            }

            // Search component
            SearchComponent(onSearchClicked = onSearchClicked)

            // List of recipes
            RecipesList(recipes = recipes)
        }
    }
}