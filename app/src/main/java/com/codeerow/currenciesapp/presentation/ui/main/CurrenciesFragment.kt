package com.codeerow.currenciesapp.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codeerow.currenciesapp.R
import org.koin.android.viewmodel.ext.android.viewModel


class CurrenciesFragment : Fragment() {

    private val viewModel by viewModel<CurrenciesViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }
}
