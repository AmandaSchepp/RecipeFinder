package com.example.recipefinder.data.network

import com.example.recipefinder.data.model.Meal
import com.example.recipefinder.data.model.MealResponse

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

// Create a singleton object for the MealApiClient
object MealApiClient {

    // Initialize a Ktor HttpClient with CIO engine and ContentNegotiation for JSON serialization
    private val apiClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    // Function to retrieve a random recipe from the API
    suspend fun getRandomRecipe(): List<Meal> {
        // Define the URL for the random recipe endpoint
        val url = "https://www.themealdb.com/api/json/v1/1/random.php"
        // Make a GET request to the API and parse the response into MealResponse
        val response = apiClient.get(url).body() as MealResponse
        // Return the list of meals from the response
        return response.meals
    }

    // Function to retrieve recipes based on a search query from the API
    suspend fun getSearchedRecipe(query: String): List<Meal> {
        // Define the URL for the search recipe endpoint with the provided query
        val url = "https://www.themealdb.com/api/json/v1/1/search.php?s=$query"
        // Make a GET request to the API and parse the response into MealResponse
        val response = apiClient.get(url).body() as MealResponse
        // Return the list of meals from the response
        return response.meals
    }
}