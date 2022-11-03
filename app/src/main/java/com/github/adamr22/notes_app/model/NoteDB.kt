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
}