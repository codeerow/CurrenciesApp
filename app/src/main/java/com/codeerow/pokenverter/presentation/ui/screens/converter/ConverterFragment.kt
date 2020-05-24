package com.codeerow.pokenverter.presentation.ui.screens.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codeerow.pokenverter.R
import com.codeerow.pokenverter.presentation.ui.core.databinding.dataBinding
import com.codeerow.pokenverter.presentation.ui.core.view.recycler_view.MarginItemDecoration
import com.codeerow.pokenverter.presentation.ui.screens.converter.list.RateAdapter
import kotlinx.android.synthetic.main.converter_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class ConverterFragment : Fragment() {

    private val viewModel by viewModel<ConverterViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        return inflater.inflate(R.layout.converter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding {
            with(currencies) {
                val rateAdapter = RateAdapter(viewModel.rates.value ?: listOf()) {
                    viewModel.setAnchor(it)
                    scrollToPosition(0)
                }
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = rateAdapter

                setHasFixedSize(true)
                addItemDecoration(MarginItemDecoration(horizontalMargin = 16, verticalMargin = 16))

                bind(viewModel.rates) { newRates -> rateAdapter.updateItems(newRates) }
            }
        }
    }
}
