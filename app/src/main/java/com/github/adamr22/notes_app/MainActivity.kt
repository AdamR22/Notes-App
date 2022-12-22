package com.github.adamr22.notes_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.github.adamr22.notes_app.views.ViewNotes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES) AppCompatDelegate.setDefaultNightMode(
            MODE_NIGHT_YES
        )

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ViewNotes.newInstance()).commit()
    }

}