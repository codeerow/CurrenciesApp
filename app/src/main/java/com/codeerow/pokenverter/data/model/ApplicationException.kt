package com.codeerow.pokenverter.data.model

import androidx.annotation.StringRes


class ApplicationException(
    @StringRes
    val messageRes: Int,
    throwable: Throwable? = null
) : RuntimeException(throwable)