package com.juliensacre.rickmortydating.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object AndroidUtil {
    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    fun calcultateNumberOfColumn(context: Context) : Int{
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels /displayMetrics.density
        return (dpWidth / 180).toInt()
    }
}