package com.codeerow.currenciesapp.data.network.connectivity.impl

import com.codeerow.currenciesapp.data.network.connectivity.ConnectivityProvider
import com.codeerow.currenciesapp.data.network.connectivity.ConnectivityProvider.ConnectivityStateListener
import com.codeerow.currenciesapp.data.network.connectivity.ConnectivityProvider.NetworkState


abstract class ConnectivityProviderBaseImpl : ConnectivityProvider {
//    private val handler = Handler(Looper.getMainLooper())
    private val listeners = mutableSetOf<ConnectivityStateListener>()
    private var subscribed = false


    override fun addListener(listener: ConnectivityStateListener) {
        listeners.add(listener)
        listener.onStateChange(getNetworkState()) // propagate an initial state
        verifySubscription()
    }

    override fun removeListener(listener: ConnectivityStateListener) {
        listeners.remove(listener)
        verifySubscription()
    }

    private fun verifySubscription() {
        if (!subscribed && listeners.isNotEmpty()) {
            subscribe()
            subscribed = true
        } else if (subscribed && listeners.isEmpty()) {
            unsubscribe()
            subscribed = false
        }
    }

    protected fun dispatchChange(state: NetworkState) {
//        handler.post {
            for (listener in listeners) {
                listener.onStateChange(state)
            }
//        }
    }
}