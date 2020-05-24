package com.codeerow.pokenverter.presentation.ui.core.databinding

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


class LiveDataBinding(private val lifecycleOwner: LifecycleOwner) {

    fun <P, V : View> V.bind(property: LiveData<P>, binding: V.(P) -> Unit) {
        property.observe(lifecycleOwner, Observer {
            binding(it)
        })
    }
}