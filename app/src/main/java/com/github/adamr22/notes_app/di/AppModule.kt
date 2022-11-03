package com.github.adamr22.notes_app.di

import android.content.Context
import androidx.room.Room
import com.github.adamr22.notes_app.model.NoteDB
import com.github.adamr22.notes_app.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): NoteDB {
        return Room.databaseBuilder(context, NoteDB::class.java, "note_db").build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: NoteDB): NoteRepository {
        return NoteRepository(db)
    }
}