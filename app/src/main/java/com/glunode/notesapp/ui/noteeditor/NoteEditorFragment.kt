// Created by abdif on 8/28/2020

package com.glunode.notesapp.ui.noteeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glunode.notesapp.R
import com.glunode.notesapp.databinding.FragmentNoteEditorBinding
import com.glunode.notesapp.model.Note
import com.glunode.notesapp.utils.getFormattedTime
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note_editor.*

@AndroidEntryPoint
class NoteEditorFragment : Fragment() {

    private lateinit var binding: FragmentNoteEditorBinding
    private val viewModel by viewModels<NoteEditorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_note_editor, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val timestamp = "Created ${getFormattedTime(System.currentTimeMillis() / 1000)}"
        timestampText.text = timestamp

        binding.navigateBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.popupBtn.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.menu.add("Save")
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.title) {
                    "Save" -> {
                        saveNote()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    private fun saveNote() {
        val title = noteTitleET.text.toString()
        val desc = noteDescET.text.toString()
        if (desc.isEmpty())
            Snackbar.make(root, "Note is required!", Snackbar.LENGTH_SHORT).show()
        else {
            val note = Note().also {
                it.title = title
                it.desc = desc
                it.createdAt = System.currentTimeMillis() / 1000
            }
            viewModel.saveNote(note)
            findNavController().navigateUp()
        }
    }
}