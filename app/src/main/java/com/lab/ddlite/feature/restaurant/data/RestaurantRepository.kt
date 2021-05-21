package com.lab.ddlite.feature.restaurant.data

import com.lab.ddlite.common.Failure
import com.lab.ddlite.common.Outcome
import com.lab.ddlite.common.Success
import com.lab.ddlite.feature.common.Repository
import com.lab.ddlite.feature.restaurant.ListRestaurantsUseCase
import com.lab.ddlite.feature.restaurant.Restaurant
import com.lab.ddlite.feature.restaurant.data.remote.RestaurantRemoteDataRepository
import com.lab.ddlite.feature.restaurant.data.remote.restaurant.toRestaurant
import com.lab.ddlite.feature.restaurant.data.remote.storefeed.toRestaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


interface RestaurantRepository : Repository {
    fun get(id: Int): Flow<Outcome<Restaurant, Throwable>>
    fun list(params: ListRestaurantsUseCase.Params): Flow<Outcome<List<Restaurant>, Throwable>>
}

// TODO: Pass Dispatcher and CoroutineScope
class RestaurantRepositoryImpl(
    private val remoteRepo: RestaurantRemoteDataRepository
) : RestaurantRepository {

    override fun get(id: Int): Flow<Outcome<Restaurant, Throwable>> {
        return flow {
            val result = when (val result = remoteRepo.getRestaurant(id)) {
                is Success -> {
                    Success(result.value.toRestaurant())
                }
                is Failure -> {
                    Failure(result.reason)
                }
            }
            emit(result)
        }
    }

    override fun list(params: ListRestaurantsUseCase.Params): Flow<Outcome<List<Restaurant>, Throwable>> {
        return flow {
            val result = when (val result = remoteRepo.listRestaurants(params)) {
                is Success -> {
                    Success(result.value.map { it.toRestaurant() })
                }
                is Failure -> {
                    Failure(result.reason)
                }
            }
            emit(result)
        }
    }
}