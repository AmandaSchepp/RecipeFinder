package com.example.recipefinder

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import com.google.firebase.auth.FirebaseAuth

// Definition of the NotesViewModel class, extending ViewModel
class NotesViewModel : ViewModel() {

    // Initialize Firebase Firestore instance
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Reference to the "notes" collection in Firestore
    private val notesCollection: CollectionReference = db.collection("notes")

    // Firebase authentication instance
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Coroutine function to add a note to the Firestore database
    suspend fun addNote(content: String) {
        // Retrieve the current user's ID
        val userId = auth.currentUser?.uid
        userId?.let {
            // Perform the operation on the IO dispatcher
            withContext(Dispatchers.IO) {
                // Create a Note object with the provided content and user ID
                val note = Note(
                    content = content,
                    userId = userId
                )
                // Add the note to the Firestore collection and wait for the operation to complete
                notesCollection.add(note).await()
            }
        }
    }

    // Coroutine function to retrieve a list of notes from the Firestore database
    suspend fun getNotes(): List<Note> {
        // Retrieve the current user's ID
        val userId = auth.currentUser?.uid
        return userId?.let {
            // Perform the operation on the IO dispatcher
            withContext(Dispatchers.IO) {
                // Query the Firestore collection for notes belonging to the current user
                notesCollection
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                    // Convert the result to a list of Note objects
                    .toObjects(Note::class.java)
            }
        } ?: emptyList() // Return an empty list if the user ID is null
    }

    // Coroutine function to delete a note from the Firestore database
    suspend fun deleteNote(note: Note) {
        // Retrieve the current user's ID
        val userId = auth.currentUser?.uid
        userId?.let {
            // Perform the operation on the IO dispatcher
            withContext(Dispatchers.IO) {
                // Query the Firestore collection for the target note(s) and delete them
                notesCollection
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("content", note.content) // You may need to adjust this based on what uniquely identifies your notes
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            notesCollection.document(document.id).delete()
                        }
                    }
                    .await()
            }
        }
    }
}