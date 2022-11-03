package com.github.adamr22.notes_app.views

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    companion object {
        fun newInstance() = WriteEditNoteFragment()
    }

    private var selectedPhotoUri: Uri? = null

    private val selectPhotoIntent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { photoUri ->
        selectedPhotoUri = photoUri
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
            R.id.insert_photo -> {
                selectPhotoIntent.launch("image/*")
                true
            }

            R.id.delete_note -> {
                if (noteId == null) Toast.makeText(
                    requireContext(),
                    "Can't delete unsaved note",
                    Toast.LENGTH_SHORT
                ).show()

                noteId?.let {
                    // TODO: Delete note and navigate back to view notes screen
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
        }

        viewLifecycleOwner.lifecycleScope.launch {
            noteId?.let {
                viewModel.getNote(it).collectLatest { note ->
                    binding.noteModel = note
                }
                binding.fabSaveNote.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.edit_note
                    )
                )
            }
        }

        binding.fabSaveNote.setOnClickListener {
            if (noteId == null) {
                viewModel.saveNote(
                    Note(
                        title = binding.etNoteTitle.text.toString(),
                        content = binding.etNoteContent.text.toString(),
                        photoUri = selectedPhotoUri,
                        timeCreated = Date()
                    )
                )
                Toast.makeText(requireContext(), "Note saved", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }

        super.onResume()
    }


    override fun onDestroy() {
        selectPhotoIntent.unregister()
        super.onDestroy()
    }
}