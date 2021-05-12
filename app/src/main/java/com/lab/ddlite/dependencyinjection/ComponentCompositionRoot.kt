package com.lab.ddlite.dependencyinjection

import com.lab.ddlite.feature.restaurant.GetRestaurantUseCase
import com.lab.ddlite.feature.restaurant.GetRestaurantUseCaseImpl
import com.lab.ddlite.feature.restaurant.ListRestaurantsUseCase
import com.lab.ddlite.feature.restaurant.ListRestaurantsUseCaseImpl
import com.lab.ddlite.feature.restaurant.data.RestaurantRepository
import com.lab.ddlite.feature.restaurant.data.RestaurantRepositoryImpl
import com.lab.ddlite.feature.restaurant.data.remote.RestaurantRemoteDataRepository
import com.lab.ddlite.ui.common.viewmodel.ViewModelFactory
import com.lab.ddlite.ui.common.viewproxy.ViewProxyFactory

/**
 * Scoped to Component
 */
class ComponentCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    private val layoutInflater get() = activityCompositionRoot.layoutInflater
    private val fragmentManager get() = activityCompositionRoot.fragmentManager
    val activity get() = activityCompositionRoot.activity

    private val remoteApi get() = activityCompositionRoot.remoteApi

    private val restaurantRepository: RestaurantRepository
        get() =
            RestaurantRepositoryImpl(
                RestaurantRemoteDataRepository(
                    remoteApi
                )
            )

    // use cases
    private val listRestaurantsUseCase: ListRestaurantsUseCase
        get() = ListRestaurantsUseCaseImpl(restaurantRepository)

    private val getRestaurantsUseCase: GetRestaurantUseCase
        get() = GetRestaurantUseCaseImpl(restaurantRepository)


    val screensNavigator get() = activityCompositionRoot.screensNavigator
    val viewProxyFactory get() = ViewProxyFactory()

    val viewModelFactory get() = ViewModelFactory(listRestaurantsUseCase, getRestaurantsUseCase)
}