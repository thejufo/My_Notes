// Created by abdif on 8/28/2020

package com.glunode.notesapp.ui.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.glunode.notesapp.R
import com.glunode.notesapp.databinding.FragmentNotesListBinding
import com.glunode.notesapp.model.Note
import com.glunode.notesapp.utils.ItemSpacingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notes_list.*
import timber.log.Timber

@AndroidEntryPoint
class NotesListFragment : Fragment(), NotesAdapter.OnNoteClickListener {

    private lateinit var binding: FragmentNotesListBinding
    private val viewModel by viewModels<NotesListViewModel>()
    private val notesAdapter = NotesAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesListBinding.inflate(inflater)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.addItemDecoration(ItemSpacingDecoration(requireContext(), R.dimen.size_medium))
        recyclerView.adapter = notesAdapter

        navigateToEditor.setOnClickListener {
            Timber.e("Onclick")
            viewModel.navigateToEditor()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.notesList.observe(viewLifecycleOwner, Observer {
            notesAdapter.notes = it.toMutableList()
        })

        viewModel.navigateToEditor.observe(viewLifecycleOwner, Observer {
            if (it) {
                navigateToEditor()
                viewModel.navigateToEditorDone()
            }
        })
    }

    private fun navigateToEditor() {
        val action = NotesListFragmentDirections.notesListToNoteEditor(null)
        findNavController().navigate(action)
    }

    override fun onNoteClick(root: View, note: Note) {
        val action = NotesListFragmentDirections.notesListToNoteDetails(note.id)
        findNavController().navigate(action)
    }
}