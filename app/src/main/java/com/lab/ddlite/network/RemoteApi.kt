package com.lab.ddlite.network

import com.lab.ddlite.feature.restaurant.data.remote.restaurant.RestaurantResponse
import com.lab.ddlite.feature.restaurant.data.remote.storefeed.StoreFeedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RemoteApi {
    @GET("/v2/restaurant/{id}")
    fun getRestaurant(@Path("id") id: Int): Call<RestaurantResponse>

    @GET("/v1/store_feed/")
    fun getStoreFeed(
        @Query("lat") lat: Double, @Query("lng") lng: Double
        , @Query("offset") offset: Int, @Query("limit") limit: Int
    ): Call<StoreFeedResponse>
}