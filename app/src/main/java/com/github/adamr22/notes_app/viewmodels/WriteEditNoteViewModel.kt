package com.github.adamr22.notes_app.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adamr22.notes_app.model.Note
import com.github.adamr22.notes_app.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WriteEditNoteViewModel @Inject constructor(private val repository: NoteRepository) :
    ViewModel() {

    fun getNote(noteId: Int) = repository.getNote(noteId)

    fun saveNote(note: Note) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.saveNote(note)
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun updateNote(title: String, content: String, noteImage: Uri?, noteId: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.updateNote(title, content, noteImage, noteId)
        }
    }
}