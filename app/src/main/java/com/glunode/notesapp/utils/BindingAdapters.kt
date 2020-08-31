// Created by abdif on 8/29/2020

package com.glunode.notesapp.utils

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.glunode.notesapp.model.Note

@BindingAdapter("isGone")
fun View.isGone(bool: Boolean) {
    this.isVisible = !bool
}

@BindingAdapter("setFormattedDateText")
fun TextView.setFormattedDateText(note: Note?) {
    text = ""
    note.takeUnless { it == null }?.apply {
        val timestamp = if (modifiedAt == -1L)
            "Created ${getFormattedTime(createdAt)}"
        else "Modified ${getFormattedTime(modifiedAt)}"
        text = timestamp
    }
}

