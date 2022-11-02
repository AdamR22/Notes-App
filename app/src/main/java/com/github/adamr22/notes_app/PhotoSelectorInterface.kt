package com.github.adamr22.notes_app

import android.net.Uri

interface PhotoSelectorInterface {
    fun selectPhoto()
    fun selectedPhoto(): Uri?
}