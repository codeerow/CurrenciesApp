package com.codeerow.currenciesapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codeerow.currenciesapp.R
import com.codeerow.currenciesapp.presentation.ui.screens.converter.ConverterFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            attachFragment()
        }
    }

    private fun attachFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ConverterFragment())
            .commitNow()
    }
}
