package com.lab.ddlite.ui.restaurantlist

import com.lab.ddlite.feature.restaurant.Restaurant


data class RestaurantListItemUIModel(
    val id: Int,
    val name: String,
    val subtext: String,
    val imageUrl: String,
    val isClosed: Boolean,
    val orderTime: Int
)

fun Restaurant.toRestaurantListItemUIModel(): RestaurantListItemUIModel {

    return RestaurantListItemUIModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        subtext = description,
        isClosed = !isOpen,
        orderTime = orderTimeRange.first()
    )
}

fun RestaurantListItemUIModel.toRestaurant(): Restaurant {
    return Restaurant(
        id = id,
        name = name,
        imageUrl = imageUrl,
        description = subtext,
        isOpen = !isClosed,
        orderTimeRange = listOf(orderTime, orderTime)
    )
}

