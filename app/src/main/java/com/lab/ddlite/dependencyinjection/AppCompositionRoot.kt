package com.lab.ddlite.dependencyinjection

import android.app.Application
import androidx.annotation.UiThread
import com.lab.ddlite.network.ApiClient
import com.lab.ddlite.network.RemoteApi

/**
 * Scoped to Application
 */
@UiThread
class AppCompositionRoot(val application: Application) {

    private val apiClient: ApiClient by lazy {
        ApiClient()
    }

    val remoteAPi: RemoteApi by lazy {
        apiClient.retrofit.create(RemoteApi::class.java)
    }
}