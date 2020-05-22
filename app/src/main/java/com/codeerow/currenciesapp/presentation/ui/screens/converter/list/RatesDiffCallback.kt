package com.codeerow.currenciesapp.presentation.ui.screens.converter.list

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.codeerow.currenciesapp.presentation.model.Currency
import timber.log.Timber


class RatesDiffCallback(
    private var oldRates: List<Pair<Currency, Double>>,
    private var newRates: List<Pair<Currency, Double>>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRates[oldItemPosition].first.id == newRates[newItemPosition].first.id
    }

    override fun getOldListSize() = oldRates.size
    override fun getNewListSize() = newRates.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRates[oldItemPosition].second == newRates[newItemPosition].second
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//        Timber.d("oldElement: ${oldRates}")
//        Timber.d("newElement: ${newRates}")
//        Timber.d(" ")

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