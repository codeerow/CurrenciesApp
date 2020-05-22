package com.codeerow.currenciesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codeerow.currenciesapp.ui.main.CurrenciesFragment
import kotlinx.android.synthetic.main.main_activity.*

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
            .replace(R.id.container, CurrenciesFragment.newInstance())
            .commitNow()
    }
}
