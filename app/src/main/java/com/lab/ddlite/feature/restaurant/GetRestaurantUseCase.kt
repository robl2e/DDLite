package com.lab.ddlite.feature.restaurant

import com.lab.ddlite.common.Outcome
import com.lab.ddlite.feature.common.UseCase
import com.lab.ddlite.feature.restaurant.data.RestaurantRepository
import kotlinx.coroutines.flow.Flow

//TODO: Pass coroutineDispatcher
interface GetRestaurantUseCase : UseCase {
    operator fun invoke(id: Int): Flow<Outcome<Restaurant, Throwable>>
}

class GetRestaurantUseCaseImpl(private val restaurantRepository: RestaurantRepository) :
    GetRestaurantUseCase {

    override fun invoke(id: Int): Flow<Outcome<Restaurant, Throwable>> {
        return restaurantRepository.get(id)
    }
}