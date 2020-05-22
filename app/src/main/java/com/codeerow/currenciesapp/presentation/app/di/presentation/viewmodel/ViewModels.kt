package com.codeerow.currenciesapp.presentation.app.di.presentation.viewmodel

import com.codeerow.currenciesapp.presentation.ui.screens.converter.ConverterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModels = module {
    viewModel { ConverterViewModel(get()) }
}

