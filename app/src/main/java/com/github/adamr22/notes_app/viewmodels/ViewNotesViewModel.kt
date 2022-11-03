package com.github.adamr22.notes_app.viewmodels

import androidx.lifecycle.ViewModel
import com.github.adamr22.notes_app.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewNotesViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    fun getAllNotes() = repository.getAllNotes()
}