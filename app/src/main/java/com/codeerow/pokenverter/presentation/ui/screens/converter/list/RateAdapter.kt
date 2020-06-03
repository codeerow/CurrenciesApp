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
    private val onAnchorChanged: (Pair<String, BigDecimal>) -> Unit
) : RecyclerView.Adapter<RateViewHolder>() {

    private val entities = initialEntities.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_item_currency, parent, false)
        return RateViewHolder(onAnchorChanged, view)
    }

    override fun getItemCount(): Int = entities.size

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val item = entities[position]
        holder.bind(item)
    }

    override fun onBindViewHolder(
        holder: RateViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
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