package com.github.adamr22.notes_app.repository

import android.net.Uri
import com.github.adamr22.notes_app.model.Note
import com.github.adamr22.notes_app.model.NoteDao

class NoteRepository(private val noteDao: NoteDao) {
    fun getAllNotes() = noteDao.getAllNotes()

    fun getNote(noteId: Int) = noteDao.getNote(noteId)

    suspend fun saveNote(note: Note) = noteDao.saveNote(note)

    suspend fun updateNote(noteTitle: String, photoUri: Uri, content: String, noteId: Int) =
        noteDao.updateNote(noteTitle, content, noteId)

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
}