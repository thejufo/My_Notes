// Created by abdif on 8/28/2020

package com.glunode.notesapp.ui.noteslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.glunode.notesapp.databinding.ItemNoteBinding
import com.glunode.notesapp.model.Note

class NotesAdapter(private val onNoteClickListener: OnNoteClickListener) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>(), Filterable {

    private var filteredNotes: List<Note>? = null

    var notes = mutableListOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.from(parent, onNoteClickListener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        filteredNotes?.get(position)?.let { note ->
            holder.bind(note)
        }
    }

    override fun getItemCount() = filteredNotes?.count() ?: 0

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                filteredNotes = if (charString.isEmpty()) notes
                else {
                    notes.filter {
                        it.title.contains(charString, true) || it.desc.contains(
                            charString
                        )
                    }
                }
                val results = FilterResults()
                results.values = filteredNotes
                return results
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                filteredNotes = filterResults.values as ArrayList<Note>
                notifyDataSetChanged()
            }
        }
    }


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