package com.codeerow.currenciesapp.data.network.connectivity.impl


import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.codeerow.currenciesapp.data.network.connectivity.ConnectivityProvider
import com.codeerow.currenciesapp.data.network.connectivity.ConnectivityProvider.NetworkState.Connected
import com.codeerow.currenciesapp.data.network.connectivity.ConnectivityProvider.NetworkState.NotConnectedState


@RequiresApi(Build.VERSION_CODES.N)
class ConnectivityProviderImpl(private val cm: ConnectivityManager) :
    ConnectivityProviderBaseImpl() {

    private val networkCallback = ConnectivityCallback()

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    override fun subscribe() {
        cm.registerDefaultNetworkCallback(networkCallback)
    }

    override fun unsubscribe() {
        cm.unregisterNetworkCallback(networkCallback)
    }


    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    override fun getNetworkState(): ConnectivityProvider.NetworkState {
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return if (capabilities != null) {
            Connected.Above24API(capabilities)
        } else {
            NotConnectedState
        }
    }

    private inner class ConnectivityCallback : ConnectivityManager.NetworkCallback() {

        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            dispatchChange(Connected.Above24API(capabilities))
        }

        override fun onLost(network: Network) {
            dispatchChange(NotConnectedState)
        }
    }
}