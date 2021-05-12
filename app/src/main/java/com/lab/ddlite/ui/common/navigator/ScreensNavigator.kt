package com.lab.ddlite.ui.common.navigator

import android.app.Activity
import com.lab.ddlite.feature.restaurant.Restaurant
import com.lab.ddlite.ui.restaurantdetail.RestaurantDetailActivity

interface ScreensNavigator {
    fun navigateBack()
    fun toRestaurantDetail(id: Int, restaurant: Restaurant?)
}

class ScreensNavigatorImpl(private val activity: Activity) : ScreensNavigator {
    override fun navigateBack() {
        activity.onBackPressed()
    }

    override fun toRestaurantDetail(id: Int, restaurant: Restaurant?) {
        RestaurantDetailActivity.start(activity, id, restaurant);
    }
}