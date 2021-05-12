package com.lab.ddlite.feature.restaurant.data.remote.storefeed

data class PopularItem(
    val description: String,
    val id: Int,
    val img_url: String,
    val name: String,
    val price: Int
)