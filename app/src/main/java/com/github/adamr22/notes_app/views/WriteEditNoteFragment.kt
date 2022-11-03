package com.github.adamr22.notes_app.views

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.adamr22.notes_app.R
import com.github.adamr22.notes_app.databinding.FragmentWriteEditNoteBinding
import com.github.adamr22.notes_app.model.Note
import com.github.adamr22.notes_app.viewmodels.WriteEditNoteViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class WriteEditNoteFragment : Fragment() {

    private val NOTE_ID_TAG = "note_id_tag"
    var noteId: Int? = null

    private var note: Note? = null

    companion object {
        fun newInstance() = WriteEditNoteFragment()
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[WriteEditNoteViewModel::class.java]
    }

    private lateinit var binding: FragmentWriteEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        noteId = arguments?.getInt(NOTE_ID_TAG)
        setHasOptionsMenu(true)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarEditWriteNotes)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.delete_note -> {
                if (noteId == null) Toast.makeText(
                    requireContext(),
                    "Can't delete unsaved note",
                    Toast.LENGTH_SHORT
                ).show()

                noteId?.let {
                    viewModel.deleteNote(
                        Note(
                            note?.id,
                            note?.title!!,
                            note?.content!!,
                            note?.timeCreated!!
                        )
                    )

                    Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }

                true
            }

            R.id.change_theme -> {
                // TODO: Change theme from light to dark mode and vice versa
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {

        binding.toolbarEditWriteNotes.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        if (noteId == null) {
            binding.noteModel = null
            binding.fabSaveNote.visibility = View.VISIBLE
        }

        viewLifecycleOwner.lifecycleScope.launch {
            noteId?.let {
                binding.fabEditNote.visibility = View.VISIBLE
                binding.fabSaveNote.visibility = View.GONE
                viewModel.getNote(it).collectLatest { gottenNote ->
                    note = gottenNote
                    binding.noteModel = note
                }
            }
        }

        binding.fabSaveNote.setOnClickListener {

            viewModel.saveNote(
                Note(
                    title = binding.etNoteTitle.text.toString(),
                    content = binding.etNoteContent.text.toString(),
                    timeCreated = Date()
                )
            )
            Toast.makeText(requireContext(), "Note saved", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()

        }

        binding.fabEditNote.setOnClickListener {
            viewModel.updateNote(
                noteId = noteId!!,
                title = binding.etNoteTitle.text.toString(),
                content = binding.etNoteContent.text.toString(),
            )
            Toast.makeText(requireContext(), "Note edited", Toast.LENGTH_SHORT).show()
        }

        super.onResume()
    }
}