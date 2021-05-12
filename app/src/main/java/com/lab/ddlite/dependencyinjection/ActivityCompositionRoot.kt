package com.lab.ddlite.dependencyinjection

import android.view.LayoutInflater
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.lab.ddlite.ui.common.navigator.ScreensNavigator
import com.lab.ddlite.ui.common.navigator.ScreensNavigatorImpl

/**
 * Scoped to Activity
 */
@UiThread
class ActivityCompositionRoot(
    val activity: AppCompatActivity, private val appCompositionRoot: AppCompositionRoot
) {

    val screensNavigator: ScreensNavigator by lazy {
        ScreensNavigatorImpl(activity)
    }

    // Android platform
    val application get() = appCompositionRoot.application
    val layoutInflater: LayoutInflater get() = LayoutInflater.from(activity)
    val fragmentManager get() = activity.supportFragmentManager

    // Apis
    val remoteApi get() = appCompositionRoot.remoteAPi
}