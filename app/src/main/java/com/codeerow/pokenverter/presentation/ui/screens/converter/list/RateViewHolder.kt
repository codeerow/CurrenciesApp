package com.codeerow.pokenverter.presentation.ui.screens.converter.list

import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.codeerow.pokenverter.presentation.ui.core.getCurrencyIconId
import com.codeerow.pokenverter.presentation.ui.core.getCurrencyName
import kotlinx.android.synthetic.main.layout_item_currency.view.*
import java.math.BigDecimal
import java.text.DecimalFormat


class RateViewHolder(
    private val onAnchorChanged: (Pair<String, BigDecimal>) -> Unit,
    view: View
) : RecyclerView.ViewHolder(view) {
    private val amountFormat = DecimalFormat("#0.00")


    fun bind(item: Pair<String, BigDecimal>) = with(itemView) {
        val (currency, rate) = item
        code.text = currency
        with(itemView.context) {
            icon.setImageResource(getCurrencyIconId(currency))
            name.text = getCurrencyName(currency)
        }
        itemView.setOnClickListener { amount.requestFocus() }
        amount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && layoutPosition > 0) setAnchor(currency, amount.text.toString())
        }

        amount.doOnTextChanged { text, _, _, _ ->
            if (amount.isFocused) setAnchor(currency, text.toString())
        }

        bindAmount(rate)
    }

    fun bindAmount(rate: BigDecimal) = with(itemView) {
        if (!amount.isFocused) {
            if (rate > BigDecimal.ZERO) amount.setText(amountFormat.format(rate))
            else amount.setText("")
        }
    }


    private fun setAnchor(currency: String, amountText: String?) {
        onAnchorChanged(
            currency to if (amountText.isNullOrBlank()) BigDecimal.ZERO else BigDecimal(
                amountFormat.parse(amountText.toString())?.toDouble() ?: 0.0
            )
        )
    }
}