package com.codeerow.pokenverter.presentation.app.di

import com.codeerow.pokenverter.data.di.data
import com.codeerow.pokenverter.presentation.app.di.domain.domain
import com.codeerow.pokenverter.presentation.app.di.presentation.presentation


val app = data + domain + presentation
