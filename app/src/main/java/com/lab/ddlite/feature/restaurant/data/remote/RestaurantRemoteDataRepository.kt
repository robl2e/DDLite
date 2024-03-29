package com.lab.ddlite.feature.restaurant.data.remote

import com.lab.ddlite.common.Failure
import com.lab.ddlite.common.Outcome
import com.lab.ddlite.common.Success
import com.lab.ddlite.feature.restaurant.ListRestaurantsUseCase
import com.lab.ddlite.feature.restaurant.data.remote.restaurant.RestaurantResponse
import com.lab.ddlite.feature.restaurant.data.remote.storefeed.Store
import com.lab.ddlite.network.RemoteApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//TODO: Pass Dispatcher and CoroutineScope
class RestaurantRemoteDataRepository(private val remoteApi: RemoteApi) {

    suspend fun getRestaurant(id: Int): Outcome<RestaurantResponse, Throwable> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteApi.getRestaurant(id).execute()
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Success(response.body()!!)
                } else {
                    return@withContext Failure(
                        Exception(response.message())
                    )
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext Failure(t)
                } else {
                    throw t
                }
            }
        }
    }

    suspend fun listRestaurants(params: ListRestaurantsUseCase.Params): Outcome<List<Store>, Throwable> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteApi.getStoreFeed(
                    lat = params.lat,
                    lng = params.lng,
                    offset = params.offset,
                    limit = params.limit
                ).execute()

                if (response.isSuccessful && response.body() != null) {
                    return@withContext Success(response.body()!!.stores)
                } else {
                    return@withContext Failure(
                        Exception(response.message())
                    )
                }
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext Failure(t)
                } else {
                    throw t
                }
            }
        }
    }
}