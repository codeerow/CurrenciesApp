package com.codeerow.currenciesapp.presentation.ui.screens.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codeerow.currenciesapp.R
import com.codeerow.currenciesapp.presentation.model.Currency
import com.codeerow.currenciesapp.presentation.ui.core.databinding.dataBinding
import com.codeerow.currenciesapp.presentation.ui.core.view.recycler_view.MarginItemDecoration
import com.codeerow.currenciesapp.presentation.ui.screens.converter.list.CurrencyAdapter
import com.codeerow.currenciesapp.presentation.ui.screens.converter.list.RatesDiffCallback
import kotlinx.android.synthetic.main.converter_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class ConverterFragment : Fragment() {

    private val viewModel by viewModel<ConverterViewModel>()
    private var rates = mutableListOf<Pair<Currency, Double>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        return inflater.inflate(R.layout.converter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding {
            with(currencies) {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = CurrencyAdapter(viewModel.rates)

                setHasFixedSize(true)
                addItemDecoration(MarginItemDecoration(horizontalMargin = 16, verticalMargin = 16))

                bind(viewModel.rates) { newRates ->
                    val diffCallback = RatesDiffCallback(rates, newRates)
                    val diffResult = DiffUtil.calculateDiff(diffCallback)
                    diffResult.dispatchUpdatesTo(adapter!!)
                    rates.clear()
                    rates.addAll(newRates)
                }
            }
        }
    }
}
