// Created by abdif on 8/27/2020

package com.glunode.notesapp.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.glunode.notesapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_notes_list.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    init {
        lifecycleScope.launchWhenCreated {
            setContentView(R.layout.activity_main)
        }
    }
}