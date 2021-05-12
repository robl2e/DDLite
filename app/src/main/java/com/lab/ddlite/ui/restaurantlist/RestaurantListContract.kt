package com.lab.ddlite.ui.restaurantlist

import com.lab.ddlite.feature.restaurant.Restaurant
import com.lab.ddlite.ui.common.mvx.MvxEffect
import com.lab.ddlite.ui.common.mvx.MvxEvent
import com.lab.ddlite.ui.common.mvx.MvxState


interface RestaurantListContract {
    interface Listener {
        fun onEvent(event: ViewEvent)
    }

    sealed class ViewState : MvxState {
        object IdleState : ViewState()
        data class LoadingState(val show: Boolean) : ViewState()
        data class DataState(val uiModels: List<RestaurantListItemUIModel>) : ViewState()
    }

    sealed class ViewEvent : MvxEvent {
        object LoadEvent : ViewEvent()
        data class ItemClickEvent(val uiModel: RestaurantListItemUIModel) : ViewEvent()
    }

    sealed class ViewEffect : MvxEffect {
        data class ShowError(val reason: Throwable) : ViewEffect()
        data class NavigateToDetailScreen(val id: Int, val restaurant: Restaurant?) : ViewEffect()
    }
}