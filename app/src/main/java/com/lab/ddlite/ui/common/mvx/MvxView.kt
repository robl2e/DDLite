package com.lab.ddlite.ui.common.mvx

/**
 *  View that renders by state as part
 *  of presentation/ui architecture
 */
interface MvxView {
    fun render(state: MvxState)
}
