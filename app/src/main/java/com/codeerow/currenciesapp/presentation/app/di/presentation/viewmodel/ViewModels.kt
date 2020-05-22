package com.codeerow.currenciesapp.presentation.app.di.presentation.viewmodel

import com.codeerow.currenciesapp.presentation.ui.main.CurrenciesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModels = module {
    viewModel { CurrenciesViewModel() }
}

