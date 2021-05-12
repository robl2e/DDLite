package com.lab.ddlite.ui.common.mvx

import kotlinx.coroutines.flow.StateFlow

/**
 * Handles presentation separation for UI <-> Bus logic
 * presentation/ui architecture
 */
interface MvxController {
    val viewState: StateFlow<MvxState>
    fun process(event: MvxEvent)
}


