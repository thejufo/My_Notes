// Created by abdif on 8/28/2020

package com.glunode.notesapp.ui.noteeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.glunode.notesapp.databinding.FragmentNoteEditorBinding
import com.glunode.notesapp.model.Note
import com.glunode.notesapp.utils.getFormattedTime
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note_editor.*
import java.util.*

@AndroidEntryPoint
class NoteEditorFragment : Fragment() {

    private lateinit var binding: FragmentNoteEditorBinding
    private val viewModel by viewModels<NoteEditorViewModel>()
    private val args by navArgs<NoteEditorFragmentArgs>()
    private val note by lazy { args.note }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteEditorBinding.inflate(inflater)
        binding.note = note
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val timestamp = "Created ${getFormattedTime(System.currentTimeMillis() / 1000)}"
        timestampText.text = timestamp

        binding.navigateBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveNoteBtn.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = noteTitleET.text.toString()
        val desc = noteDescET.text.toString()
        if (desc.isEmpty())
            Snackbar.make(root, "Note is required!", Snackbar.LENGTH_SHORT).show()
        else {
            val note = note ?: Note().also {
                val id = UUID.randomUUID().toString()
                it.id = id
            }
            note.also {
                it.title = title
                it.desc = desc
                it.createdAt = System.currentTimeMillis() / 1000
            }
            viewModel.saveNote(note)
            findNavController().navigateUp()
        }
    }
}