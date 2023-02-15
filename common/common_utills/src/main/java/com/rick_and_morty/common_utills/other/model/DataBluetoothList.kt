package com.rick_and_morty.common_utills.other.model

import android.bluetooth.BluetoothDevice

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 12/11/2022.
 */
open class DataBluetoothList(open var id: Int)
data class DataDevice(override var id: Int, val name: String, var device: BluetoothDevice,var address:String, var isConnected: Boolean = false) : DataBluetoothList(id)
data class DataCut(override var id: Int, val title: String) : DataBluetoothList(id)