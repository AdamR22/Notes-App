package com.github.adamr22.notes_app.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.adamr22.notes_app.R
import com.github.adamr22.notes_app.viewmodels.ViewNotesViewModel

class ViewNotes : Fragment() {

    companion object {
        fun newInstance() = ViewNotes()
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[ViewNotesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_notes, container, false)
    }

}