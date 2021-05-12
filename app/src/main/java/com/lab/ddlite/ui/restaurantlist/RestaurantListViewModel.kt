package com.lab.ddlite.ui.restaurantlist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.lab.ddlite.common.Failure
import com.lab.ddlite.common.Success
import com.lab.ddlite.feature.restaurant.ListRestaurantsUseCase
import com.lab.ddlite.ui.common.mvx.BaseMvxControllerViewModel
import com.lab.ddlite.ui.common.mvx.MvxEvent
import com.lab.ddlite.ui.restaurantlist.RestaurantListContract.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RestaurantListViewModel(private val listRestaurantsUseCase: ListRestaurantsUseCase) :
    BaseMvxControllerViewModel<ViewEvent, ViewState, ViewEffect>
        (ViewState.IdleState) {

    companion object {
        val TAG: String = RestaurantListViewModel::class.java.simpleName
    }

    override fun process(event: MvxEvent) {
        when (event) {
            is ViewEvent.LoadEvent -> {
                listRestaurants()
            }
            is ViewEvent.ItemClickEvent -> {
                navigateToDetailScreen(event.uiModel)
            }
        }
    }

    private fun navigateToDetailScreen(uiModel: RestaurantListItemUIModel) {
        postEffect { ViewEffect.NavigateToDetailScreen(uiModel.id, uiModel.toRestaurant()) }
    }

    private fun listRestaurants() {
        viewModelScope.launch {
            setState { ViewState.LoadingState(true) }
            try {
                listRestaurantsUseCase().onStart {
                    setState { ViewState.LoadingState(false) }
                }.onEach { result ->
                    when (result) {
                        is Success -> {
                            val uiModels = result.value.map {
                                it.toRestaurantListItemUIModel()
                            }
                            if (uiModels.isEmpty()) {
                                setState { ViewState.IdleState }
                            } else {
                                setState { ViewState.DataState(uiModels) }
                            }
                        }
                        is Failure -> {
                            Log.e(TAG, "Unable to fetch restaurants", result.reason)
                            postEffect { ViewEffect.ShowError(result.reason) }
                        }
                    }
                }.collect()
            } catch (e: Throwable) {
                postEffect { ViewEffect.ShowError(e) }
                setState { ViewState.LoadingState(false) }
            }
        }
    }
}