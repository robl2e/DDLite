package com.lab.ddlite

import android.app.Application
import com.lab.ddlite.dependencyinjection.AppCompositionRoot

class CustomApplication: Application() {

    lateinit var appCompositionRoot: AppCompositionRoot

    override fun onCreate() {
        appCompositionRoot = AppCompositionRoot(this)
        super.onCreate()
    }

}