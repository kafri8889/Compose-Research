package com.anafthdev.composeresearch.research.navigate_with_result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Screen1State(
    val text: String = ""
): Parcelable
