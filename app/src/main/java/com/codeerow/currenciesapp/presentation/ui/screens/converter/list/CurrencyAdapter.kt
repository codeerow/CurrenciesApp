package com.codeerow.currenciesapp.presentation.ui.screens.converter.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.codeerow.currenciesapp.R
import com.codeerow.currenciesapp.presentation.model.Currency
import com.codeerow.currenciesapp.presentation.ui.screens.converter.list.RatesDiffCallback.Companion.PAYLOAD_NEW_AMOUNT
import kotlinx.android.synthetic.main.layout_item_currency.view.*
import timber.log.Timber


class CurrencyAdapter(private val entities: LiveData<List<Pair<Currency, Double>>>) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = itemView.title
        private val description = itemView.description
        private val value = itemView.value

        fun bind(item: Pair<Currency, Double>) {
            val (currency, amount) = item
            title.text = currency.title
            description.text = currency.description
            bindAmount(amount)
        }

        fun bindAmount(amount: Double) {
            value.setText(amount.toString())
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.layout_item_currency, parent, false))
    }

    override fun getItemCount(): Int = entities.value?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = entities.value?.get(position)
        item?.let(holder::bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else {
            Timber.d("bindAmount")
            val bundle = payloads[0] as Bundle
            bundle.keySet().forEach { key ->
                if (key == PAYLOAD_NEW_AMOUNT) {
                    val amount = entities.value?.get(position)?.second
                    amount?.let(holder::bindAmount)
                }
            }
        }
    }
}