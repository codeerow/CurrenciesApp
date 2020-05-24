package com.codeerow.currenciesapp.presentation.ui.screens.converter.list

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import java.math.BigDecimal


class RateDiffCallback(
    private var oldRates: List<Pair<String, BigDecimal>>,
    private var newRates: List<Pair<String, BigDecimal>>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRates[oldItemPosition].first == newRates[newItemPosition].first
    }

    override fun getOldListSize() = oldRates.size
    override fun getNewListSize() = newRates.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRates[oldItemPosition].second == newRates[newItemPosition].second
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldElement = oldRates[oldItemPosition]
        val newElement = newRates[newItemPosition]

        val diff = Bundle()
        if (oldElement.second != newElement.second) diff.putByte(PAYLOAD_NEW_AMOUNT, -1)
        return if (diff.isEmpty) null else diff
    }

    companion object {
        const val PAYLOAD_NEW_AMOUNT = "PAYLOAD_NEW_AMOUNT"
    }
}