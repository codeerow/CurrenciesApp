package com.codeerow.pokenverter.presentation.ui.core.databinding

import androidx.lifecycle.LifecycleOwner


fun LifecycleOwner.dataBinding(form: LiveDataBinding.() -> Unit): LiveDataBinding {
    val dataBinding = LiveDataBinding(this)
    form(dataBinding)
    return dataBinding
}