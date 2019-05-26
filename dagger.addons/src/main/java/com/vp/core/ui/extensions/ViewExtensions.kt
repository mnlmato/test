package com.vp.core.ui.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflateCustomView(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true) =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)