package com.example.recipefinder.sign_in

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ViewModel class for managing the sign-in state
class SignInViewModel : ViewModel() {

    // MutableStateFlow to represent the state of the sign-in process
    private val _state = MutableStateFlow(SignInState())

    // Immutable StateFlow representing the state for external observation
    val state = _state.asStateFlow()

    // Function to update the sign-in state based on the result
    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,  // Set to true if sign-in is successful
            signInError = result.errorMessage            // Set the error message if sign-in fails
        ) }
    }

    // Function to reset the sign-in state
    fun resetState() {
        _state.update { SignInState() }  // Reset the state to the default values
    }
}