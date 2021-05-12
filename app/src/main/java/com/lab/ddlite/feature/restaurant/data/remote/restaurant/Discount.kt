package com.lab.ddlite.feature.restaurant.data.remote.restaurant

data class Discount(
    val amount: Amount,
    val description: String,
    val discount_type: String,
    val min_subtotal: MinSubtotal,
    val source_type: String,
    val text: String
)