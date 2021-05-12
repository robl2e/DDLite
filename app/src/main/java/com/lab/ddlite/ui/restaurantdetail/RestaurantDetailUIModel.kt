package com.lab.ddlite.ui.restaurantdetail

import com.lab.ddlite.feature.restaurant.Restaurant
import java.math.BigDecimal

data class RestaurantDetailUIModel(
    val name: String,
    val id: Int,
    val description: String,
    val imageUrl: String,
    val isOpen: Boolean,
    val orderTime: Int,
    val rating: Float = 0f
)

fun Restaurant.toRestaurantDetailUIModel(): RestaurantDetailUIModel {
    return RestaurantDetailUIModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        description = description,
        isOpen = isOpen,
        orderTime = orderTimeRange.first(),
        rating = computeNormalizedRating(rating)
    )
}

private fun computeNormalizedRating(rating: Double): Float {
    val bigDecimal = BigDecimal(rating / 2)
    return bigDecimal.toFloat()
}
