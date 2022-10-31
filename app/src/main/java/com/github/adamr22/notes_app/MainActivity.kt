package com.github.adamr22.notes_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.adamr22.notes_app.views.ViewNotes

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ViewNotes.newInstance()).commit()
    }
}