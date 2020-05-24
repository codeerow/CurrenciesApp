package com.codeerow.pokenverter.presentation.ui.screens.converter.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codeerow.pokenverter.presentation.ui.core.getCurrencyIconId
import com.codeerow.pokenverter.presentation.ui.core.getCurrencyName
import kotlinx.android.synthetic.main.layout_item_currency.view.*
import java.math.BigDecimal

class ViewHolder(
    private val onTouchItem: (Pair<String, BigDecimal>) -> Unit, view: View
) : RecyclerView.ViewHolder(view) {

    private val code = itemView.code
    private val name = itemView.name
    private val amount = itemView.amount
    private val icon = itemView.icon

    fun bind(item: Pair<String, BigDecimal>) {
        val (currency, rate) = item
        code.text = currency
        with(itemView.context) {
            icon.setImageResource(getCurrencyIconId(currency))
            name.text = getCurrencyName(currency)
        }
        itemView.setOnClickListener {
            amount.requestFocus()
            onTouchItem(item)
        }
        amount.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) onTouchItem(item) }
        bindAmount(rate)
    }

    fun bindAmount(value: BigDecimal) {
        amount.setText("$value")
    }
}