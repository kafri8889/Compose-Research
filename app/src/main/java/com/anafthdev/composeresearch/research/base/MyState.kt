package com.anafthdev.composeresearch.research.base

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyState(
    val text: String = ""
): Parcelable
