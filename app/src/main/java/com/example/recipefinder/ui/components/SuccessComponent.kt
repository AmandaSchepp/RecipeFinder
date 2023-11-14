package com.example.recipefinder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipefinder.data.model.Meal
import com.example.recipefinder.sign_in.UserData

@Composable
fun SuccessComponent(
    recipes: List<Meal>,
    onSearchClicked: (query: String) -> Unit,
    userData: UserData?,
    onSignOut: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
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

            if(userData?.username != null) {
                Text(
                    text = userData.username,
                    textAlign = TextAlign.Right,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(
                            top = 5.dp,
                            start = 100.dp,
                            end = 10.dp
                        ),
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (userData?.profilePictureUrl != null) {
                AsyncImage(
                    model = userData.profilePictureUrl,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(
                            top = 5.dp,
                            end = 5.dp
                        )
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        SearchComponent(onSearchClicked = onSearchClicked)
        RecipesList(recipes = recipes)
    }
}