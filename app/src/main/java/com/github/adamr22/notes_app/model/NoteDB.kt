package com.github.adamr22.notes_app.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Note::class], version = 1)
@TypeConverters(Converters::class)
abstract class NoteDB : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDB? = null

        fun getInstance(context: Context): NoteDB {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context,
                    NoteDB::class.java,
                    "note_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }

}