package com.example.recipefinder

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import com.google.firebase.auth.FirebaseAuth

class NotesViewModel : ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val notesCollection: CollectionReference = db.collection("notes")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun addNote(content: String) {
        val userId = auth.currentUser?.uid
        userId?.let {
            withContext(Dispatchers.IO) {
                val note = Note(
                    content = content,
                    userId = userId
                )
                notesCollection.add(note).await()
            }
        }
    }

    suspend fun getNotes(): List<Note> {
        val userId = auth.currentUser?.uid
        return userId?.let {
            withContext(Dispatchers.IO) {
                notesCollection
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                    .toObjects(Note::class.java)
            }
        } ?: emptyList()
    }

    suspend fun deleteNote(note: Note) {
        val userId = auth.currentUser?.uid
        userId?.let {
            withContext(Dispatchers.IO) {
                // Find the note by userId and delete it
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
