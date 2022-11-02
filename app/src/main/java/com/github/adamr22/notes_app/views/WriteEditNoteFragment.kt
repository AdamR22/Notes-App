package com.github.adamr22.notes_app.views

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.adamr22.notes_app.PhotoSelectorInterface
import com.github.adamr22.notes_app.R
import com.github.adamr22.notes_app.databinding.FragmentWriteEditNoteBinding
import com.github.adamr22.notes_app.viewmodels.WriteEditNoteViewModel
import com.squareup.picasso.Picasso
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

    private val selectedPhotoCallback by lazy {
        requireActivity() as PhotoSelectorInterface
    }

    private var selectedPhotoUri: Uri? = null

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

        binding.toolbarEditWriteNotes.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.insert_photo -> {
                selectedPhotoCallback.selectPhoto()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {

        if (noteId == null) binding.noteModel = null

        if (selectedPhotoCallback.selectedPhoto() != null) {
            selectedPhotoUri = selectedPhotoCallback.selectedPhoto()
            Toast.makeText(requireContext(), "Note Photo Selected", Toast.LENGTH_SHORT).show()
        }

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