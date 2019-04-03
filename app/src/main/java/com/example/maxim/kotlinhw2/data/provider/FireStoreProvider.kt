package com.example.maxim.kotlinhw2.data.provider

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.maxim.kotlinhw2.data.errors.NoAuthException
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.model.Result
import com.example.maxim.kotlinhw2.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel


private const val NOTES_COLLECTION = "notes"

class FireStoreProvider(private val firebaseAuth: FirebaseAuth, private val db: FirebaseFirestore) :
        RemoteDataProvider {

    private val TAG = "${FireStoreProvider::class.java.simpleName} :"
    private val USERS_COLLECTION = "users"

    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    override suspend fun saveNote(note: Note): Note =
        suspendCoroutine { continuation ->
            try {
                getUserNotesCollection().document(note.id)
                        .set(note).addOnSuccessListener {
                            Log.d(TAG, "Note $note is saved")
                            continuation.resume(note)
                        }.addOnFailureListener{
                            Log.d(TAG, "Error saving note $note, message: ${it.message}")
                            continuation.resumeWithException(it)
                        }
            } catch (e: Throwable){
                continuation.resumeWithException(e)
            }
        }

    override fun subscribeToAllNotes(): ReceiveChannel<Result> =
    Channel<Result>(Channel.CONFLATED).apply{
        var registration: ListenerRegistration? = null

        try {
            registration = getUserNotesCollection().addSnapshotListener{
                snapshot, e ->
                val value = e?.let{
                    Result.Error(it)
                } ?: snapshot?.let{
                    val notes = it.documents.map { it.toObject(Note::class.java) }
                    Result.Success(notes)
                }
                value?.let { offer(it) }
            }
        } catch (e: Throwable) {
            offer(Result.Error(e))
        }
        invokeOnClose { registration?.remove() }
    }

    override suspend fun getNoteById(id: String): Note =
        suspendCoroutine {continuation ->
            try {
                getUserNotesCollection().document(id).get()
                        .addOnSuccessListener {
                            continuation.resume(it.toObject(Note::class.java)!!)
                        }.addOnFailureListener {
                            continuation.resumeWithException(it)
                        }
            } catch (e: Throwable){
                continuation.resumeWithException(e)
            }
        }

    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override suspend fun getCurrentUser() : User? =
            suspendCoroutine { continuation ->
                continuation.resume(
                        currentUser?.let {
                            User(it.displayName ?: "", it.email ?: "") })
            }

    override suspend fun deleteNote(noteId: String) : Unit =
            suspendCoroutine { continuation ->
                getUserNotesCollection().document(noteId).delete()
                        .addOnSuccessListener {
                            continuation.resume(Unit)
                        }
                        .addOnFailureListener {
                            continuation.resumeWithException(it)
                        }
            }
}