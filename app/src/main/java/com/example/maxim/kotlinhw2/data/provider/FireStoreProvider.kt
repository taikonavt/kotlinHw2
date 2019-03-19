package com.example.maxim.kotlinhw2.data.provider

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.model.NoteResult
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.*

private const val NOTES_COLLECTION = "notes"

class FireStoreProvider : RemoteDataProvider {
    private val TAG = "${FireStoreProvider::class.java.simpleName} :"

    private val db = FirebaseFirestore.getInstance()

    private val notesReference = db.collection(NOTES_COLLECTION)

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.document(note.id)
                .set(note)
                .addOnSuccessListener {
                    Log.d(TAG, "Note $note is saved")
                    result.value = NoteResult.Success(note)
                }.addOnFailureListener{
                    OnFailureListener { p0 ->
                        Log.d(TAG, "Error saving note $note, message: ${p0.message}")
                        result.value = NoteResult.Error(p0)
                    }
                }
        return result
    }

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.addSnapshotListener { snapshot, e ->
            if (e != null){
                result.value = NoteResult.Error(e)
            } else if (snapshot != null){
                val notes = mutableListOf<Note>()

                for (doc : QueryDocumentSnapshot in snapshot){
                    notes.add(doc.toObject(Note::class.java))
                }
                result.value = NoteResult.Success(notes)
            }
        }
        return result
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.document(id).get()
                .addOnSuccessListener { snapshot -> result.value =
                        NoteResult.Success(snapshot.toObject(Note::class.java)) }
                .addOnFailureListener { result.value = NoteResult.Error(it) }
        return result
    }
}