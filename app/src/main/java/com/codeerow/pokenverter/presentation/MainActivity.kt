package com.codeerow.pokenverter.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codeerow.pokenverter.R
import com.codeerow.pokenverter.presentation.ui.screens.converter.ConverterFragment

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
