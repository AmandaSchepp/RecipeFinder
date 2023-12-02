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

@Composable
fun NotesScreen(
    onBackClicked: () -> Unit,
    notesViewModel: NotesViewModel = viewModel()
) {
    var newNoteText by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf<List<Note>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(notesViewModel) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                notes = notesViewModel.getNotes()
            }
        }

        onDispose {
            coroutineScope.cancel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display existing notes
        LazyColumn(
            modifier = Modifier.weight(1f),
            content = {
                items(notes) { note ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFd7edc7))
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Text with weight
                            Text(
                                text = note.content,
                                modifier = Modifier.weight(1f)
                            )

                            // Delete icon with fixed width
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        withContext(Dispatchers.IO) {
                                            // Delete the note from Firestore
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
                                notesViewModel.addNote(newNoteText)
                                notes = notesViewModel.getNotes()
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




