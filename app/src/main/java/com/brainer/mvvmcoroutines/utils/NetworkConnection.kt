package com.brainer.mvvmcoroutines.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkConnection(private val mainActivity: Activity) {
    fun isConnected(): Boolean {
        val info: NetworkInfo = this.getNetworkInfo()!!
        return info != null && info.isAvailable && info.isConnected
    }

    //private internal methods
    private fun getNetworkInfo(): NetworkInfo? {
        val connectivityManager =
            mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo
    }
}