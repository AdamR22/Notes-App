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
import com.github.adamr22.notes_app.databinding.NoteItemCardLayoutBinding
import com.github.adamr22.notes_app.model.Note
import com.github.adamr22.notes_app.views.WriteEditNoteFragment

class ViewNotesAdapter(private val fa: FragmentManager) :
    RecyclerView.Adapter<ViewNotesAdapter.NoteItemViewHolder>() {

    private val NOTE_ID_TAG = "note_id_tag"

    inner class NoteItemViewHolder(val binding: NoteItemCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noteModel: Note) {
            binding.noteModel = noteModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {

        val binding: NoteItemCardLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.note_item_card_layout,
            parent,
            false
        )

        return NoteItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        val data = data.currentList[position]

        holder.bind(data)

        holder.binding.noteItemCard.setOnClickListener {
            cardClicked(data.id)
        }

    }

    override fun getItemCount(): Int {
        return data.currentList.size
    }


    private fun cardClicked(noteId: Int?) {
        val writeEditNoteFragment = WriteEditNoteFragment.newInstance().apply {
            noteId?.let {
                val bundle = Bundle().also { bund ->
                    bund.putInt(NOTE_ID_TAG, it)
                }

                this.arguments = bundle
            }
        }

        fa.beginTransaction().replace(R.id.fragment_container, writeEditNoteFragment).addToBackStack(null).commit()
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