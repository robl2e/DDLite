package com.lab.ddlite.feature.restaurant

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.lab.ddlite.common.BaseTestCase
import com.lab.ddlite.common.Failure
import com.lab.ddlite.common.Outcome
import com.lab.ddlite.common.Success
import com.lab.ddlite.feature.restaurant.data.RestaurantRepository
import com.lab.ddlite.feature.restaurant.data.remote.storefeed.Store
import com.lab.ddlite.feature.restaurant.data.remote.storefeed.toRestaurant
import com.lab.ddlite.util.getJsonStringFromResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.io.IOException

class ListRestaurantsUseCaseTest : BaseTestCase() {

    private lateinit var listRestaurantsUseCase: ListRestaurantsUseCase
    private lateinit var fakeRestaurantRepository: FakeRestaurantRepository
    private val restaurants = getRestaurants()

    override fun before() {
        super.before()
        fakeRestaurantRepository = FakeRestaurantRepository(restaurants)
        listRestaurantsUseCase = ListRestaurantsUseCaseImpl(fakeRestaurantRepository)
    }

    @Test
    fun `list restaurants successfully`() {
        runBlocking {
            listRestaurantsUseCase().collect {
                val expected = Success<List<Restaurant>, Throwable>(restaurants)
                val result = it as Success
                Assert.assertEquals(expected, it)
                Assert.assertEquals(expected.value, result.value)
            }
        }
    }

    @Test
    fun `list restaurants generic error`() {
        runBlocking {
            fakeRestaurantRepository.scenario = FakeRestaurantRepository.Scenario.GeneralError
            listRestaurantsUseCase().collect {
                Assert.assertTrue(it is Failure)
            }
        }
    }


    @Test
    fun `receive multiple results successfully`() {
        runBlocking {
            fakeRestaurantRepository.scenario = FakeRestaurantRepository.Scenario.MultiSuccess
            val expectedSize = 2
            val results = listRestaurantsUseCase().toList()
            for (outcome in results) {
                Assert.assertTrue(outcome is Success)
            }
            Assert.assertEquals(expectedSize, results.size)
        }
    }

    @Test
    fun `receive mixed success then failure results`() {
        runBlocking {
            fakeRestaurantRepository.scenario =
                FakeRestaurantRepository.Scenario.MixedSuccessFailure
            val expectedSize = 2
            val results = listRestaurantsUseCase().toList()

            Assert.assertTrue(results[0] is Success)
            Assert.assertTrue(results[1] is Failure)
            Assert.assertEquals(expectedSize, results.size)
        }
    }


    // ---------------------------------------------------------------------------------------------
    // Helpers
    //
    private fun getRestaurants(): List<Restaurant> {
        val classLoader = this.javaClass.classLoader
        val jsonString = getJsonStringFromResource(classLoader, "store_feed.json")

        val gson = GsonBuilder().create()
        val listType = object : TypeToken<List<Store>>() {}.type
        val stores: List<Store> = gson.fromJson(jsonString, listType)
        return stores.map { it.toRestaurant() }
    }

    private class FakeRestaurantRepository(
        var restaurants: List<Restaurant>,
    ) : RestaurantRepository {

        var scenario: Scenario = Scenario.Success

        sealed class Scenario {
            object Success : Scenario()
            object MultiSuccess : Scenario()
            object MixedSuccessFailure : Scenario()
            object GeneralError : Scenario()
        }

        override fun get(id: Int): Flow<Outcome<Restaurant, Throwable>> {
            TODO("Not yet implemented")
        }

        override fun list(params: ListRestaurantsUseCase.Params): Flow<Outcome<List<Restaurant>, Throwable>> {
            return flow {
                when (scenario) {
                    is Scenario.MixedSuccessFailure -> {
                        emit(Success<List<Restaurant>, Throwable>(restaurants.subList(0, 1)))
                        delay(300) // simulate network delay
                        emit(Failure<List<Restaurant>, Throwable>(IOException("")))
                    }
                    is Scenario.MultiSuccess -> {
                        emit(Success<List<Restaurant>, Throwable>(restaurants.subList(0, 1)))
                        delay(300) // simulate network delay
                        emit(Success<List<Restaurant>, Throwable>(restaurants.subList(1, 2)))
                    }
                    is Scenario.GeneralError -> {
                        emit(Failure<List<Restaurant>, Throwable>(RuntimeException("")))
                    }
                    else ->
                        emit(Success<List<Restaurant>, Throwable>(restaurants))
                }
            }
        }
    }
}