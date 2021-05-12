package com.lab.ddlite.ui.restaurantdetail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.lab.ddlite.common.Failure
import com.lab.ddlite.common.Success
import com.lab.ddlite.feature.restaurant.GetRestaurantUseCase
import com.lab.ddlite.ui.common.mvx.BaseMvxControllerViewModel
import com.lab.ddlite.ui.common.mvx.MvxEvent
import com.lab.ddlite.ui.restaurantdetail.RestaurantDetailContract.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RestaurantDetailViewModel(
    private val getRestaurantUseCase: GetRestaurantUseCase
) : BaseMvxControllerViewModel<ViewEvent, ViewState, ViewEffect>(ViewState.IdleState) {

    companion object {
        val TAG: String = RestaurantDetailViewModel::class.java.simpleName
    }

    override fun process(event: MvxEvent) {
        when (event) {
            is ViewEvent.InitializeEvent -> {
                setState { ViewState.DataState(event.uiModel) }
            }
            is ViewEvent.LoadEvent -> {
                getRestaurant(event.id)
            }
            is ViewEvent.NavBackEvent -> {
                postEffect { ViewEffect.NavBack }
            }
        }
    }

    private fun getRestaurant(id: Int) {
        viewModelScope.launch {
            try {
                getRestaurantUseCase(id).collect { result ->
                    when (result) {
                        is Success -> {
                            setState {
                                val uiModel = result.value.toRestaurantDetailUIModel()
                                ViewState.DataState(uiModel)
                            }
                        }
                        is Failure -> {
                            Log.e(TAG, "Unable to fetch restaurant", result.reason)
                            postEffect { ViewEffect.ShowError(result.reason) }
                        }
                    }
                }
            } catch (e: Throwable) {
                postEffect { ViewEffect.ShowError(e) }
            }
        }
    }
}
