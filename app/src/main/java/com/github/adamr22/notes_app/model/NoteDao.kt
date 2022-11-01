package com.github.adamr22.notes_app.model

import android.net.Uri
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNote(noteId: Int): Flow<Note>

    @Insert
    suspend fun saveNote(note: Note)

    @Query(
        """
            UPDATE notes
            SET title = :noteTitle, photoUri = :photoUri, content = :content
            WHERE id = :noteId
        """
    )
    suspend fun updateNote(noteTitle: String, photoUri: Uri, content: String, noteId: Int)

    @Delete
    suspend fun deleteNote(note: Note)

}