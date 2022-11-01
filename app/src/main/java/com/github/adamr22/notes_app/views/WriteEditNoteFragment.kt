package com.github.adamr22.notes_app.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.github.adamr22.notes_app.R
import com.github.adamr22.notes_app.databinding.FragmentWriteEditNoteBinding
import com.github.adamr22.notes_app.viewmodels.WriteEditNoteViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WriteEditNoteFragment : Fragment() {

    private val NOTE_ID_TAG = "note_id_tag"
    var noteId: Int? = null

    companion object {
        fun newInstance() = WriteEditNoteFragment()
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[WriteEditNoteViewModel::class.java]
    }

    private lateinit var binding: FragmentWriteEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        noteId = arguments?.getInt(NOTE_ID_TAG)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_write_edit_note, container, false)

        return binding.root
    }

    override fun onResume() {

        if (noteId == null) binding.noteModel = null

        viewLifecycleOwner.lifecycleScope.launch {
            noteId?.let {
                viewModel.getNote(it).collectLatest { note ->
                    binding.noteModel = note
                }
            }
        }
        super.onResume()
    }
}