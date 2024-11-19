package com.example.capstone.ui.history

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class History (
    val name : String,
    val category:String,
    val photo : String,
    val rank : String
): Parcelable