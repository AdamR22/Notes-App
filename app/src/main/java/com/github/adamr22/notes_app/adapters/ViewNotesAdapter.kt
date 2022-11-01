package com.github.adamr22.notes_app.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.adamr22.notes_app.R
import com.github.adamr22.notes_app.databinding.AddNoteCardLayoutBinding
import com.github.adamr22.notes_app.databinding.NoteItemCardLayoutBinding
import com.github.adamr22.notes_app.model.Note
import com.github.adamr22.notes_app.views.WriteEditNoteFragment

class ViewNotesAdapter(private val fa: FragmentManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_FIRST_ITEM = 0
    private val TYPE_ITEM = 1

    private val NOTE_ID_TAG = "note_id_tag"

    inner class NoteItemViewHolder(val binding: NoteItemCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(noteModel: Note) {
            binding.noteModel = noteModel
        }
    }

    inner class AddNoteViewHolder(val binding: AddNoteCardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_FIRST_ITEM -> {
                val binding: AddNoteCardLayoutBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.add_note_card_layout,
                    parent,
                    false
                )

                AddNoteViewHolder(binding)
            }
            TYPE_ITEM -> {
                val binding: NoteItemCardLayoutBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.note_item_card_layout,
                    parent,
                    false
                )

                NoteItemViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == TYPE_FIRST_ITEM) {
            val addNoteItemViewHolder = holder as AddNoteViewHolder

            addNoteItemViewHolder.binding.addNoteCard.setOnClickListener {
                cardClicked(null)
            }
        }

        if (holder.itemViewType == TYPE_ITEM) {
            val data = data.currentList[position - 1]

            val noteItemHolder: NoteItemViewHolder = holder as NoteItemViewHolder

            noteItemHolder.bind(data)

            noteItemHolder.binding.noteItemCard.setOnClickListener {
                 cardClicked(data.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_FIRST_ITEM else TYPE_ITEM
    }

    private fun cardClicked(noteId: Int?) {
        val writeEditNoteFragment = WriteEditNoteFragment.newInstance().apply {
            noteId?.let {
                val bundle = Bundle().also{ bund ->
                    bund.putInt(NOTE_ID_TAG, it)
                }

                this.arguments = bundle
            }
        }

        fa.beginTransaction().replace(R.id.fragment_container, writeEditNoteFragment).commit()
    }

    private val diffUtilCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val data = AsyncListDiffer(this, diffUtilCallback)
}