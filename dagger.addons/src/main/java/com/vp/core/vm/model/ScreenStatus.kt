package com.vp.core.vm.model

sealed class ScreenStatus {

    object LOADING : ScreenStatus()
    object ERROR : ScreenStatus()
    object EMPTY_SUCCESS : ScreenStatus()
    data class SUCCESS <T>(val element: T) : ScreenStatus()
}