package com.codeerow.pokenverter.data.network.connectivity

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.codeerow.pokenverter.data.network.connectivity.impl.ConnectivityProviderImpl
import com.codeerow.pokenverter.data.network.connectivity.impl.ConnectivityProviderLegacyImpl


interface ConnectivityProvider {
    @FunctionalInterface
    interface ConnectivityStateListener {
        fun onStateChange(state: NetworkState)
    }

    fun addListener(listener: ConnectivityStateListener)
    fun removeListener(listener: ConnectivityStateListener)

    fun getNetworkState(): NetworkState

    fun subscribe()
    fun unsubscribe()


    sealed class NetworkState {
        sealed class Connected : NetworkState() {
            class Below24API(networkInfo: NetworkInfo) : Connected()
            class Above24API(capabilities: NetworkCapabilities) : Connected()
        }

        object NotConnectedState : NetworkState()
    }

    companion object {
        fun createProvider(context: Context): ConnectivityProvider {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ConnectivityProviderImpl(cm)
            } else {
                ConnectivityProviderLegacyImpl(context, cm)
            }
        }
    }
}