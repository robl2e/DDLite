package com.lab.ddlite.ui.restaurantlist

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.lab.ddlite.common.*
import com.lab.ddlite.feature.restaurant.ListRestaurantsUseCase
import com.lab.ddlite.feature.restaurant.Restaurant
import com.lab.ddlite.feature.restaurant.data.remote.storefeed.Store
import com.lab.ddlite.feature.restaurant.data.remote.storefeed.toRestaurant
import com.lab.ddlite.util.getJsonStringFromResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class RestaurantListViewModelTest : BaseTestCase() {

    private var restaurants: List<Restaurant> = getRestaurants()
    private lateinit var listRestaurantsUseCase: FakeListRestaurantsUseCase
    private lateinit var viewModel: RestaurantListViewModel


    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    override fun before() {
        super.before()
        listRestaurantsUseCase = FakeListRestaurantsUseCase(restaurants)
        viewModel = RestaurantListViewModel(listRestaurantsUseCase)
    }

    @Test
    fun `process load data successfully`() {
        runBlocking {
            viewModel.process(RestaurantListContract.ViewEvent.LoadEvent)
            Assert.assertTrue(viewModel.currentState is RestaurantListContract.ViewState.DataState)
        }
    }

    @Test
    fun `load data failure shows error`() {
        runBlocking {
            listRestaurantsUseCase.scenario = FakeListRestaurantsUseCase.Scenario.GeneralError
            viewModel.process(RestaurantListContract.ViewEvent.LoadEvent)
            Assert.assertTrue(viewModel.effect.first() is RestaurantListContract.ViewEffect.ShowError)
        }
    }

    @Test
    fun `load data with multiple successes should be latest state`() {
        testCoroutineRule.testDispatcher.runBlockingTest {
            listRestaurantsUseCase.scenario = FakeListRestaurantsUseCase.Scenario.MultiSuccess

            // simulate how view listens to state changes
            launch {
                val results = mutableListOf<RestaurantListContract.ViewState>()
                viewModel.viewState.take(5)
                    .onEach { results.add(it) }
                    .onCompletion {
                        Assert.assertTrue(viewModel.currentState is RestaurantListContract.ViewState.DataState)
                        val currState = results.last() as RestaurantListContract.ViewState.DataState
                        Assert.assertTrue(currState.uiModels.size == 2)
                    }
                    .collect()
            }

            // trigger event to start
            viewModel.process(RestaurantListContract.ViewEvent.LoadEvent)
        }
    }


    @Test
    fun `select first item row should navigate to detail screen`() {
        runBlocking {
            listRestaurantsUseCase.scenario = FakeListRestaurantsUseCase.Scenario.GeneralError
            viewModel.process(
                RestaurantListContract.ViewEvent.ItemClickEvent(
                    restaurants.first().toRestaurantListItemUIModel()
                )
            )
            Assert.assertTrue(viewModel.effect.first() is RestaurantListContract.ViewEffect.NavigateToDetailScreen)
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

    private class FakeListRestaurantsUseCase(
        var restaurants: List<Restaurant>,
    ) : ListRestaurantsUseCase {

        var scenario: Scenario = Scenario.Success

        sealed class Scenario {
            object Success : Scenario()
            object MultiSuccess : Scenario()
            object GeneralError : Scenario()
        }

        override fun invoke(params: ListRestaurantsUseCase.Params): Flow<Outcome<List<Restaurant>, Throwable>> {
            return flow {
                when (scenario) {
                    Scenario.GeneralError -> {
                        emit(Failure(RuntimeException("")))
                    }
                    Scenario.MultiSuccess -> {
                        emit(Success(restaurants.subList(0, 1)))
                        delay(300) // simulate network delay
                        emit(Success(restaurants.subList(1, 3)))
                    }
                    else -> emit(Success(restaurants))
                }
            }
        }
    }
}
