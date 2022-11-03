package com.github.adamr22.notes_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.adamr22.notes_app.model.Note
import com.github.adamr22.notes_app.model.NoteDB
import com.github.adamr22.notes_app.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WriteEditNoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository =
        NoteRepository(NoteDB.getInstance(application.applicationContext).noteDao())

    fun  getNote(noteId: Int) = repository.getNote(noteId)

    fun saveNote(note:Note) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.saveNote(note)
        }
    }
}