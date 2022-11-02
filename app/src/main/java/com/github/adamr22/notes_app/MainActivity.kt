package com.github.adamr22.notes_app

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.adamr22.notes_app.views.ViewNotes

class MainActivity : AppCompatActivity(), PhotoSelectorInterface {

    private var selectedPhotoUri: Uri? = null

    private val selectPhotoIntent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { photoUri ->
        selectedPhotoUri = photoUri
        selectedPhoto()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ViewNotes.newInstance()).commit()
    }

    override fun selectPhoto() {
        selectPhotoIntent.launch("image/*")
    }

    override fun selectedPhoto(): Uri? {
        return selectedPhotoUri
    }


    override fun onDestroy() {
        selectPhotoIntent.unregister()
        super.onDestroy()
    }

}