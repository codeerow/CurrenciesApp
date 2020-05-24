package com.codeerow.pokenverter.presentation.ui.screens.converter.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codeerow.pokenverter.R
import com.codeerow.pokenverter.presentation.ui.screens.converter.list.RateDiffCallback.Companion.PAYLOAD_NEW_AMOUNT
import java.math.BigDecimal


class RateAdapter(
    initialEntities: List<Pair<String, BigDecimal>>,
    private val onTouchItem: (Pair<String, BigDecimal>) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private var entities = initialEntities.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_item_currency, parent, false)
        return ViewHolder(onTouchItem, view)
    }

    override fun getItemCount(): Int = entities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = entities[position]
        holder.itemView.setOnClickListener { onTouchItem(item) }
        holder.bind(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else {
            val bundle = payloads[0] as Bundle
            bundle.keySet().forEach { key ->
                if (key == PAYLOAD_NEW_AMOUNT) {
                    val amount = entities[position].second
                    holder.bindAmount(amount)
                }
            }
        }
    }

    fun updateItems(newEntities: List<Pair<String, BigDecimal>>) {
        val diffCallback = RateDiffCallback(entities, newEntities)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        entities.clear()
        entities.addAll(newEntities)
    }
}