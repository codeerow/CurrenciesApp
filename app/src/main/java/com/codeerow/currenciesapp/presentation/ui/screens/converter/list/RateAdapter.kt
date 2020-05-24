package com.codeerow.currenciesapp.presentation.ui.screens.converter.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.codeerow.currenciesapp.R
import com.codeerow.currenciesapp.presentation.ui.screens.converter.list.RateDiffCallback.Companion.PAYLOAD_NEW_AMOUNT
import kotlinx.android.synthetic.main.layout_item_currency.view.*
import timber.log.Timber
import java.math.BigDecimal


class RateAdapter(
    private val entities: LiveData<List<Pair<String, BigDecimal>>>,
    private val onClickListener: (Pair<String, BigDecimal>) -> Unit
) : RecyclerView.Adapter<RateAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = itemView.title
        private val description = itemView.description
        private val value = itemView.value
        private val imageView = itemView.imageView

        fun bind(item: Pair<String, BigDecimal>) {
            val (currency, amount) = item
            title.text = currency
            with(itemView.context) {
                val drawableId: Int = resources.getIdentifier(
                    "ic_" + currency.toLowerCase(), "drawable", packageName
                )
                imageView.setImageResource(drawableId)
                val currencyNameResId = resources.getIdentifier(
                    currency.toLowerCase() + "_currency_name",
                    "string",
                    packageName
                )
                if (currencyNameResId != 0) description.setText(currencyNameResId)
                else description.text = currency
            }
            bindAmount(amount)
        }

        fun bindAmount(amount: BigDecimal) {
            value.setText(amount.toString())
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.layout_item_currency, parent, false))
    }

    override fun getItemCount(): Int = entities.value?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = entities.value?.get(position) ?: return
        holder.itemView.setOnClickListener { onClickListener(item) }
//        holder.itemView.value.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) onClickListener(item)
//        }
        holder.bind(item)
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