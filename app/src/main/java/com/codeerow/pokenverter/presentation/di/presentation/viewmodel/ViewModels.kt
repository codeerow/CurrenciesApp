package com.codeerow.pokenverter.presentation.di.presentation.viewmodel

import com.codeerow.pokenverter.presentation.ui.screens.converter.ConverterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModels = module {
    viewModel {
        ConverterViewModel(observeCurrencies = get())
    }
}

