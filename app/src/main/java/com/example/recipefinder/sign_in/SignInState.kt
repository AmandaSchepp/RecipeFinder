package com.example.recipefinder.sign_in

// Data class representing the state of the sign-in process
data class SignInState (
    val isSignInSuccessful: Boolean = false,  // Flag indicating whether sign-in is successful
    val signInError: String? = null           // Error message in case of sign-in failure, null if successful
)