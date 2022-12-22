package com.github.adamr22.notes_app.repository

import android.net.Uri
import com.github.adamr22.notes_app.model.Note
import com.github.adamr22.notes_app.model.NoteDB
import com.github.adamr22.notes_app.model.NoteDao
import javax.inject.Inject

class NoteRepository @Inject constructor(db: NoteDB) {

    private val noteDao = db.noteDao()

    fun getAllNotes() = noteDao.getAllNotes()

    fun getNote(noteId: Int) = noteDao.getNote(noteId)

    suspend fun saveNote(note: Note) = noteDao.saveNote(note)

    suspend fun updateNote(noteTitle: String, content: String, noteImage: Uri?, noteId: Int) =
        noteDao.updateNote(noteTitle, content, noteImage, noteId)

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
}