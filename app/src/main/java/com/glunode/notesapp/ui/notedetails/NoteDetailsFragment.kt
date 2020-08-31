// Created by abdif on 8/28/2020

package com.glunode.notesapp.ui.notedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.glunode.notesapp.R
import com.glunode.notesapp.data.NotesRepository
import com.glunode.notesapp.databinding.FragmentNoteDetailsBinding
import com.glunode.notesapp.model.Note
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteDetailsFragment : Fragment() {


    private lateinit var binding: FragmentNoteDetailsBinding


    @Inject
    lateinit var notesRepository: NotesRepository
    private val args by navArgs<NoteDetailsFragmentArgs>()
    private val noteId by lazy { args.noteId }
    private val viewModel by viewModels<NoteDetailsViewModel> {
        NoteDetailsViewModel.Factory(
            notesRepository,
            noteId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_note_details, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigateBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.note.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                findNavController().navigateUp()
            } else {
                onUpdateUi()
            }
        })
    }

    private fun onUpdateUi() {
        viewModel.navigateToEditor.observe(viewLifecycleOwner, Observer {
            it?.run {
                navigateToEditor(this)
                viewModel.navigateToEditorDone()
            }
        })
    }

    private fun navigateToEditor(note: Note) {
        val action = NoteDetailsFragmentDirections.noteDetailsToNoteEditor(note)
        findNavController().navigate(action)
    }
}