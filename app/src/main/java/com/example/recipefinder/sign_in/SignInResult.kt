package com.example.recipefinder.sign_in

// Data class representing the result of a sign-in attempt
data class SignInResult (
    val data: UserData?,     // User data if sign-in is successful, null otherwise
    val errorMessage: String?   // Error message if sign-in fails, null otherwise
)

// Data class representing user data after a successful sign-in
data class UserData (
    val userId: String,           // Unique identifier for the user
    val username: String?,        // User's display name, nullable
    val profilePictureUrl: String? // URL of the user's profile picture, nullable
)