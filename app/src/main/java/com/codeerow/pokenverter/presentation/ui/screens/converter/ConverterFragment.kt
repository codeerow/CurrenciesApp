package com.codeerow.pokenverter.presentation.ui.screens.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codeerow.pokenverter.R
import com.codeerow.pokenverter.data.model.ApplicationException
import com.codeerow.pokenverter.domain.usecases.impl.FetchCurrencies
import com.codeerow.pokenverter.presentation.ui.core.databinding.dataBinding
import com.codeerow.pokenverter.presentation.ui.core.view.dp
import com.codeerow.pokenverter.presentation.ui.core.view.recycler_view.MarginItemDecoration
import com.codeerow.pokenverter.presentation.ui.screens.converter.list.RateAdapter
import com.codeerow.spirit.extensions.gone
import com.codeerow.spirit.extensions.visible
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.converter_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class ConverterFragment : Fragment() {

    companion object {
        private const val CONTENT_MARGIN = 16
        private const val LAYOUT_RES = R.layout.converter_fragment
    }

    private val viewModel by viewModel<ConverterViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        return inflater.inflate(LAYOUT_RES, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding {
            with(currencies) {
                val rateAdapter = RateAdapter(
                    initialEntities = listOf(),
                    onAnchorChanged = {
                        viewModel.anchor.accept(it)
                        scrollToPosition(0)
                    })

                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = rateAdapter

                setHasFixedSize(true)
                addItemDecoration(
                    MarginItemDecoration(
                        horizontalMargin = CONTENT_MARGIN.dp,
                        verticalMargin = CONTENT_MARGIN.dp
                    )
                )

                bind(viewModel.currencies) { output ->
                    hideLoading()
                    hideError()

                    when (output) {
                        is FetchCurrencies.Output.Success -> rateAdapter.submitList(output.currencies)
                        is FetchCurrencies.Output.Failure -> handleError(output.throwable)
                        is FetchCurrencies.Output.Processing -> if (output.initial) showLoading()
                    }
                }
            }
        }
    }

    /** Loading handling */
    private fun showLoading() = progressBar.visible()
    private fun hideLoading() = progressBar.gone()


    /** Error handling */
    private var snackBar: Snackbar? = null

    private fun handleError(throwable: Throwable) {
        val errorRes = (throwable as? ApplicationException)?.messageRes
            ?: R.string.application_error_default_message
        showError(errorRes)
    }

    private fun showError(@StringRes errorRes: Int) {
        view?.let {
            snackBar = Snackbar.make(it, errorRes, BaseTransientBottomBar.LENGTH_INDEFINITE)
        }
    }

    private fun hideError() = snackBar?.dismiss()
}
