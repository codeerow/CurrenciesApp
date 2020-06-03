package com.codeerow.pokenverter.presentation.ui.core

import android.content.Context
import com.codeerow.pokenverter.R
import java.util.*


fun Context.getCurrencyIconId(code: String): Int {
    val icon = resources.getIdentifier(
        "ic_" + code.toLowerCase(Locale.ROOT),
        "drawable",
        packageName
    )
    return if (icon == 0) R.drawable.ic_currency_placeholder else icon
}

fun Context.getCurrencyName(code: String): String {
    val currencyNameId = resources.getIdentifier(
        code.toLowerCase(Locale.ROOT) + "_currency_name",
        "string",
        packageName
    )
    return if (currencyNameId == 0) code
    else getString(currencyNameId)
}