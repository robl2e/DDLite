package com.lab.ddlite.common

sealed class Outcome<T, E>

data class Success<T, E>(val value: T) : Outcome<T, E>()
data class Failure<T, E>(val reason: E) : Outcome<T, E>()