// Created by abdif on 8/27/2020

package com.glunode.notesapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Note(
    var id: String,
    var title: String,
    var desc: String,
    var createdAt: Long,
    var modifiedAt: Long
) : Parcelable {

    constructor() : this("", "", "", -1L, -1L)
}