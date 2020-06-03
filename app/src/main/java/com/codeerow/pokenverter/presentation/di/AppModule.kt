package com.codeerow.pokenverter.presentation.di

import com.codeerow.pokenverter.data.di.data
import com.codeerow.pokenverter.presentation.di.domain.domain
import com.codeerow.pokenverter.presentation.di.presentation.presentation


val app = data + domain + presentation
