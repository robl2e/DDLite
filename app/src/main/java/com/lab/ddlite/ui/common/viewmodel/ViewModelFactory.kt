package com.lab.ddlite.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lab.ddlite.feature.restaurant.GetRestaurantUseCase
import com.lab.ddlite.feature.restaurant.ListRestaurantsUseCase
import com.lab.ddlite.ui.restaurantdetail.RestaurantDetailViewModel
import com.lab.ddlite.ui.restaurantlist.RestaurantListViewModel

class ViewModelFactory(
    private val listRestaurantsUseCase: ListRestaurantsUseCase,
    private val getRestaurantUseCase: GetRestaurantUseCase,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            RestaurantListViewModel::class.java -> RestaurantListViewModel(listRestaurantsUseCase) as T
            RestaurantDetailViewModel::class.java -> RestaurantDetailViewModel(getRestaurantUseCase) as T
            else -> throw RuntimeException("unsupported viewModel type: $modelClass")
        }
    }

}