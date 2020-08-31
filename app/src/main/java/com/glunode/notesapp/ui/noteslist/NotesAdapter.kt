// Created by abdif on 8/28/2020

package com.glunode.notesapp.ui.noteslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glunode.notesapp.databinding.ItemNoteBinding
import com.glunode.notesapp.model.Note

class NotesAdapter(private val onNoteClickListener: OnNoteClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    var notes = mutableListOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent, onNoteClickListener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount() = notes.count()

    class ViewHolder(
        private val binding: ItemNoteBinding,
        private val onNoteClickListener: OnNoteClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.note = note
            binding.onNoteClickListener = onNoteClickListener
            binding.executePendingBindings()
        }

        internal companion object {

            internal fun from(
                parent: ViewGroup,
                onNoteClickListener: OnNoteClickListener
            ) = ViewHolder(
                ItemNoteBinding.inflate(LayoutInflater.from(parent.context)),
                onNoteClickListener
            )
        }
    }

    interface OnNoteClickListener {

        fun onNoteClick(root: View, note: Note)
    }
}