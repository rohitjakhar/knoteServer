package com.mynote.data

import com.mongodb.ClientSessionOptions
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mynote.data.collections.Note
import com.mynote.data.collections.User
import org.litote.kmongo.MongoOperator
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.contains
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.service.MongoClientProvider

private val client = KMongo.createClient(
    MongoCredential.createCredential(
        "", "db-mongodb-nyc3-95098-093f6af2.mongo.ondigitalocean.com",
        "sfsdfsd".toCharArray()
    ).toString()
)
    .coroutine
private val database = client.getDatabase("NoteDatabase")
private val users = database.getCollection<User>()
private val notes = database.getCollection<Note>()

suspend fun registerUser(user: User): Boolean {
    return users.insertOne(user).wasAcknowledged()
}

suspend fun checkIfUserExists(email: String): Boolean {
    return users.findOne(User::email eq email) != null
}

suspend fun checkPasswordForEmail(email: String, passwordToCheck: String): Boolean {
    val actualPassword = users.findOne(User::email eq email)?.password ?: return false
    return actualPassword == passwordToCheck
}

suspend fun getNotesForUser(email: String): List<Note> {
    return notes.find(Note::owners eq email).toList()
}

suspend fun saveNote(note: Note): Boolean {
    val noteExist = notes.findOneById(note.id) != null
    return if (noteExist) {
        notes.updateOneById(note.id, note).wasAcknowledged()
    } else {
        notes.insertOne(note).wasAcknowledged()
    }
}

suspend fun isOwnerOfNote(noteId: String, owner: String): Boolean {
    val note = notes.findOneById(noteId) ?: return false
    return owner in note.owners
}

suspend fun deleteNoteForUser(email: String, noteId: String): Boolean {
    val note = notes.findOne(Note::id eq noteId, Note::owners eq email)
    note?.let { note ->
        return notes.deleteOneById(note.id).wasAcknowledged()
    } ?: return false
}