package com.github.adamr22.notes_app.model

import androidx.room.*

@Database(entities = [Note::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class NoteDB : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}