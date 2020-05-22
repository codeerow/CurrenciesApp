package com.codeerow.currenciesapp.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codeerow.currenciesapp.R

class CurrenciesFragment : Fragment() {

    companion object {
        fun newInstance() = CurrenciesFragment()
    }

    private lateinit var viewModel: CurrenciesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrenciesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
