package com.github.adamr22.notes_app.views

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.github.adamr22.notes_app.R
import com.github.adamr22.notes_app.adapters.ViewNotesAdapter
import com.github.adamr22.notes_app.databinding.FragmentViewNotesBinding
import com.github.adamr22.notes_app.viewmodels.ViewNotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewNotes : Fragment() {
    companion object {
        fun newInstance() = ViewNotes()
    }

    private val viewModel by viewModels<ViewNotesViewModel>()

    private lateinit var binding: FragmentViewNotesBinding
    private lateinit var adapter: ViewNotesAdapter

    private val THEME_TAG = "app_theme"
    private val IS_NIGHT_MODE_TAG = "is_night_mode_on"

    private val sharedPref by lazy {
        requireContext().getSharedPreferences(THEME_TAG, Context.MODE_PRIVATE)
    }

    private val isNightMode by lazy {
        sharedPref.getBoolean(IS_NIGHT_MODE_TAG, false)
    }

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
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarViewNotes)

        setUpMenu()

        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getAllNotes().collectLatest {
                adapter.data.submitList(it)
            }
        }

        binding.addNote.addNoteCard.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WriteEditNoteFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        super.onResume()
    }

    private fun setUpMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.theme_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.change_theme -> {
                        if (isNightMode) {
                            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                            sharedPref.edit().putBoolean(IS_NIGHT_MODE_TAG, false).apply()
                        }

                        if (!isNightMode) {
                            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                            sharedPref.edit().putBoolean(IS_NIGHT_MODE_TAG, true).apply()
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}