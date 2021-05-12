package com.lab.ddlite.feature.restaurant.data.remote.storefeed

data class MinimumSubtotalMonetaryFields(
    val currency: String,
    val decimal_places: Int,
    val display_string: String,
    val unit_amount: Int
)