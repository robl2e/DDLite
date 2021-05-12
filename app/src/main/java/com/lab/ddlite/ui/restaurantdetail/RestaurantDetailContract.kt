package com.lab.ddlite.ui.restaurantdetail

import com.lab.ddlite.ui.common.mvx.MvxEffect
import com.lab.ddlite.ui.common.mvx.MvxEvent
import com.lab.ddlite.ui.common.mvx.MvxState


interface RestaurantDetailContract {
    interface Listener {
        fun onEvent(event: ViewEvent)
    }

    sealed class ViewState : MvxState {
        object IdleState : ViewState()
        data class DataState(val uiModel: RestaurantDetailUIModel) : ViewState()
    }

    sealed class ViewEvent : MvxEvent {
        data class InitializeEvent(val uiModel: RestaurantDetailUIModel) : ViewEvent()
        data class LoadEvent(val id: Int) : ViewEvent()
        object NavBackEvent : ViewEvent()
    }

    sealed class ViewEffect : MvxEffect {
        object NavBack : ViewEffect()
        data class ShowError(val reason: Throwable) : ViewEffect()
    }
}