package com.codeerow.pokenverter.presentation.ui.core.view

import android.content.res.Resources


val Int.dp get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()