package com.github.adamr22.notes_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.github.adamr22.notes_app.model.NoteDB
import com.github.adamr22.notes_app.repository.NoteRepository

class ViewNotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        NoteRepository(NoteDB.getInstance(application.applicationContext).noteDao())

    fun getAllNotes() = repository.getAllNotes()
}