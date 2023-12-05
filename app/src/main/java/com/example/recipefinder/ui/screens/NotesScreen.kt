package com.example.recipefinder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipefinder.Note
import com.example.recipefinder.NotesViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

// Composable function representing the notes screen
@Composable
fun NotesScreen(
    onBackClicked: () -> Unit,
    notesViewModel: NotesViewModel = viewModel()
) {
    // State for managing the text of the new note
    var newNoteText by remember { mutableStateOf("") }

    // State for managing the list of notes
    var notes by remember { mutableStateOf<List<Note>>(emptyList()) }

    // Coroutine scope for performing asynchronous operations
    val coroutineScope = rememberCoroutineScope()

    // Use DisposableEffect to perform an action when the composable is first created and dispose of resources when it's removed
    DisposableEffect(notesViewModel) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                // Fetch the list of notes from the ViewModel
                notes = notesViewModel.getNotes()
            }
        }

        // Dispose of the coroutine scope when the composable is disposed
        onDispose {
            coroutineScope.cancel()
        }
    }

    // Column composable for arranging UI elements vertically
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display existing notes using LazyColumn
        LazyColumn(
            modifier = Modifier.weight(1f),
            content = {
                // Iterate over the list of notes
                items(notes) { note ->
                    // Box composable for creating a container with a background color
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        // Row composable for arranging UI elements horizontally
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFd7edc7))
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Text composable to display the content of the note
                            Text(
                                text = note.content,
                                modifier = Modifier.weight(1f)
                            )

                            // IconButton composable for displaying the delete icon
                            IconButton(
                                onClick = {
                                    // Perform delete operation when the delete icon is clicked
                                    coroutineScope.launch {
                                        withContext(Dispatchers.IO) {
                                            // Delete the note from Firestore using the ViewModel
                                            notesViewModel.deleteNote(note)
                                            // Update the list of notes
                                            notes = notesViewModel.getNotes()
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .width(20.dp) // Set a fixed width for the delete icon
                                    .align(Alignment.CenterVertically) // Align the icon vertically in the center
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                            }
                        }
                    }
                }
            }
        )

        // Text field for entering a new note
        OutlinedTextField(
            value = newNoteText,
            onValueChange = { newNoteText = it },
            label = { Text("\uD83D\uDDD2 Note") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )

        // Row for the Back button and Create button
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Back button
            Button(
                onClick = { onBackClicked() },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "Back")
            }

            // Create button
            Button(
                onClick = {
                    if (newNoteText.isNotBlank()) {
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                // Add a new note to Firestore using the ViewModel
                                notesViewModel.addNote(newNoteText)
                                // Update the list of notes
                                notes = notesViewModel.getNotes()
                                // Clear the text field after creating the note
                                newNoteText = ""
                            }
                        }
                    }
                },
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp)
            ) {
                Text(text = "Create")
            }
        }
    }
}



