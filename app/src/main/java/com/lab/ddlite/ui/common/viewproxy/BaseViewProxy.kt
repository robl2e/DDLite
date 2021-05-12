package com.lab.ddlite.ui.common.viewproxy

import android.content.Context
import android.view.View
import com.lab.ddlite.util.observable.Observable

/**
 *  Implementation for MvxView as a Wrapper/Proxy to android view
 */
abstract class BaseViewProxy<LISTENER>(
    private val rootView: View
) : Observable<LISTENER> {

    protected val context: Context get() = rootView.context

    private val observableDelegate = Observable.create<LISTENER>()

    override fun registerListener(listener: LISTENER) {
        observableDelegate.registerListener(listener)
    }

    override fun unregisterListener(listener: LISTENER) {
        observableDelegate.unregisterListener(listener)
    }

    override fun getListeners(): Set<LISTENER> {
        return observableDelegate.getListeners()
    }
}