package com.lab.ddlite.feature.restaurant

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    val name: String,
    val id: Int,
    val description: String,
    val imageUrl: String,
    val isOpen: Boolean,
    val orderTimeRange: List<Int>,
    val rating: Double = 0.0
) : Parcelable
