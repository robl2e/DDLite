package com.lab.ddlite.ui.common.mvx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base MvxController implemented as an androidx.lifecycle.ViewModel
 */
abstract class BaseMvxControllerViewModel<EVENT: MvxEvent, STATE : MvxState, EFFECT : MvxEffect>(initialState: STATE) :
    ViewModel(),
    MvxController {

    val currentState: STATE
        get() = viewState.value


    // view state
    private val _viewState = MutableStateFlow(initialState) // private mutable state flow
    override val viewState = _viewState.asStateFlow() // publicly exposed as read-only state flow

    // effects
    private val _effect: Channel<EFFECT> = Channel()
    val effect = _effect.receiveAsFlow()

    /**
     * Set new state for UI
     */
    protected fun setState(reduce: STATE.() -> STATE) {
        val newState = currentState.reduce()
        _viewState.value = newState
    }

    /**
     * Post effect
     */
    protected fun postEffect(builder: () -> EFFECT) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }
}