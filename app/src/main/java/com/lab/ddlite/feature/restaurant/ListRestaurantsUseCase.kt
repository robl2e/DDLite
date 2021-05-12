package com.lab.ddlite.feature.restaurant

import com.lab.ddlite.common.Outcome
import com.lab.ddlite.feature.common.UseCase
import com.lab.ddlite.feature.restaurant.data.RestaurantRepository
import kotlinx.coroutines.flow.Flow

//TODO: Pass Dispatcher
interface ListRestaurantsUseCase : UseCase {
    operator fun invoke(params: Params = Params()): Flow<Outcome<List<Restaurant>, Throwable>>

    data class Params(
        val lat: Double = 37.422740,
        val lng: Double = -122.139956,
        val offset: Int = 0,
        val limit: Int = 50
    )
}

class ListRestaurantsUseCaseImpl(private val restaurantRepository: RestaurantRepository) :
    ListRestaurantsUseCase {

    override fun invoke(params: ListRestaurantsUseCase.Params): Flow<Outcome<List<Restaurant>, Throwable>> {
        return restaurantRepository.list(params)
    }
}

