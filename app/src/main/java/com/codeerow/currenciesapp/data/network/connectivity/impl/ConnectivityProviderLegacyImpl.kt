package com.codeerow.currenciesapp.data.network.connectivity.impl

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.net.NetworkInfo
import android.net.wifi.WifiManager.EXTRA_NETWORK_INFO
import androidx.annotation.RequiresPermission
import com.codeerow.currenciesapp.data.network.connectivity.ConnectivityProvider
import com.codeerow.currenciesapp.data.network.connectivity.ConnectivityProvider.NetworkState.Connected
import com.codeerow.currenciesapp.data.network.connectivity.ConnectivityProvider.NetworkState.NotConnectedState


class ConnectivityProviderLegacyImpl(
    private val context: Context,
    private val cm: ConnectivityManager
) : ConnectivityProviderBaseImpl() {

    private val receiver = ConnectivityReceiver()

    override fun subscribe() {
        context.registerReceiver(receiver, IntentFilter(CONNECTIVITY_ACTION))
    }

    override fun unsubscribe() {
        context.unregisterReceiver(receiver)
    }

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    override fun getNetworkState(): ConnectivityProvider.NetworkState {
        val activeNetworkInfo = cm.activeNetworkInfo
        return if (activeNetworkInfo != null) {
            Connected.Below24API(activeNetworkInfo)
        } else {
            NotConnectedState
        }
    }


    private inner class ConnectivityReceiver : BroadcastReceiver() {

        @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
        override fun onReceive(c: Context, intent: Intent) {
            // on some devices ConnectivityManager.getActiveNetworkInfo() does not provide the correct network state
            // https://issuetracker.google.com/issues/37137911
            val networkInfo = cm.activeNetworkInfo
            val fallbackNetworkInfo: NetworkInfo? = intent.getParcelableExtra(EXTRA_NETWORK_INFO)
            // a set of dirty workarounds
            val state: ConnectivityProvider.NetworkState =
                if (networkInfo?.isConnectedOrConnecting == true) {
                    Connected.Below24API(networkInfo)
                } else if (networkInfo != null && fallbackNetworkInfo != null &&
                    networkInfo.isConnectedOrConnecting != fallbackNetworkInfo.isConnectedOrConnecting
                ) {
                    Connected.Below24API(fallbackNetworkInfo)
                } else {
                    val state = networkInfo ?: fallbackNetworkInfo
                    if (state != null) Connected.Below24API(state) else NotConnectedState
                }
            dispatchChange(state)
        }
    }
}