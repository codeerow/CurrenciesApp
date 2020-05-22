package com.codeerow.currenciesapp.presentation.app.di

import com.codeerow.currenciesapp.data.di.data
import com.codeerow.currenciesapp.presentation.app.di.domain.domain
import com.codeerow.currenciesapp.presentation.app.di.presentation.presentation


val app = data + domain + presentation
