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
import com.github.adamr22.notes_app.adapters.ViewNotesAdapter
import com.github.adamr22.notes_app.databinding.FragmentViewNotesBinding
import com.github.adamr22.notes_app.viewmodels.ViewNotesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViewNotes : Fragment() {
    companion object {
        fun newInstance() = ViewNotes()
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[ViewNotesViewModel::class.java]
    }

    private lateinit var binding: FragmentViewNotesBinding
    private lateinit var adapter: ViewNotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_view_notes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ViewNotesAdapter(parentFragmentManager)
        binding.adapter = adapter
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getAllNotes().collectLatest {

                binding.addNote.addNoteCard.visibility =
                    if (it.isEmpty()) View.VISIBLE else View.GONE

                adapter.data.submitList(it)
            }
        }

        binding.addNote.addNoteCard.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WriteEditNoteFragment.newInstance()).commit()
        }

        super.onResume()
    }

}