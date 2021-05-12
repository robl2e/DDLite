package com.lab.ddlite.dependencyinjection

interface Provider<T> {
    fun get(): T
}