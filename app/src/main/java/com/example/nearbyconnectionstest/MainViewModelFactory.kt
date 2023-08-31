package com.example.nearbyconnectionstest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.nearby.connection.ConnectionsClient

class MainViewModelFactory(private val connectionsClient: ConnectionsClient) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(connectionsClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}