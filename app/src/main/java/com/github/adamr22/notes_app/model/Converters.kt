package com.github.adamr22.notes_app.model

import android.net.Uri
import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun stringToUri(value: String?): Uri? {
        return Uri.parse(value) ?: null
    }

    @TypeConverter
    fun uriToString(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun timestampToDate(timeStamp: Long?): Date? {
        return timeStamp?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}