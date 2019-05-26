package com.vp.core.mapper

interface BaseMapper<in A, out B> {

    fun mapFrom(type: A): B
}
