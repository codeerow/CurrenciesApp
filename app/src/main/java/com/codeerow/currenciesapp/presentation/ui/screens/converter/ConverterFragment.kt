package com.codeerow.currenciesapp.presentation.ui.screens.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codeerow.currenciesapp.R
import com.codeerow.currenciesapp.presentation.ui.core.databinding.dataBinding
import com.codeerow.currenciesapp.presentation.ui.core.view.recycler_view.MarginItemDecoration
import com.codeerow.currenciesapp.presentation.ui.screens.converter.list.RateAdapter
import com.codeerow.currenciesapp.presentation.ui.screens.converter.list.RateDiffCallback
import kotlinx.android.synthetic.main.converter_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.math.BigDecimal


class ConverterFragment : Fragment() {

    private val viewModel by viewModel<ConverterViewModel>()
    private var rates = mutableListOf<Pair<String, BigDecimal>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        return inflater.inflate(R.layout.converter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding {
            with(currencies) {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = RateAdapter(viewModel.rates) {
                    viewModel.setAnchor(it)
                    scrollToPosition(0)
                }

                setHasFixedSize(true)
                addItemDecoration(MarginItemDecoration(horizontalMargin = 16, verticalMargin = 16))

                bind(viewModel.rates) { newCurrencies ->
                    val diffCallback = RateDiffCallback(rates, newCurrencies)
                    val diffResult = DiffUtil.calculateDiff(diffCallback)
                    diffResult.dispatchUpdatesTo(adapter!!)
                    rates.clear()
                    rates.addAll(newCurrencies)
                }
            }
        }
    }
}
