package com.rick_and_morty.common_utills.other.extention

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast


/**
 * Saidmurod Turdiyev writes this for All Project (SMT)
 */

fun Context.showToast(text: String) = if (text != "") Toast.makeText(this, text, Toast.LENGTH_SHORT).show() else null


fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork // network is currently in a high power state for performing data transmission.
    network ?: return false  // return false if network is null
    val actNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false // return false if Network Capabilities is null
    return when {
        actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> { // check if wifi is connected
            true
        }
        actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> { // check if mobile dats is connected
            true
        }
        else -> {
            false
        }
    }
}

