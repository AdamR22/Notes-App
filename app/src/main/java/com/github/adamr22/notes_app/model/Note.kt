package com.github.adamr22.notes_app.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    val title: String,

    val content: String,

    var timeCreated: Date? = null,
)