package com.lab.ddlite.common

sealed class Outcome<out T, out E>

data class Success<out T>(val value: T) : Outcome<T, Nothing>()
data class Failure<out E>(val reason: E) : Outcome<Nothing, E>()