package com.lab.ddlite.ui.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.lab.ddlite.CustomApplication
import com.lab.ddlite.dependencyinjection.ActivityCompositionRoot
import com.lab.ddlite.dependencyinjection.ComponentCompositionRoot

open class BaseActivity : AppCompatActivity() {
    private val appCompositionRoot get() = (application as CustomApplication).appCompositionRoot

    val activityCompositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot)
    }

    protected val compositionRoot by lazy {
        ComponentCompositionRoot(activityCompositionRoot)
    }
}