package com.example.nearbyconnectionstest

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.nearby.Nearby

const val SERVICE_ID = "com.example.nearbyconnectionstest"

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(Nearby.getConnectionsClient(applicationContext))
    }

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.entries.any { !it.value }) {
            Toast.makeText(this, "Required permissions needed", Toast.LENGTH_LONG).show()
            //finish()
        } else {
            recreate()
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        return permissions.isEmpty() || permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (!hasPermissions(this, REQUIRED_PERMISSIONS)) {
                requestMultiplePermissions.launch(
                    REQUIRED_PERMISSIONS
                )
            }
            NearbyConnectionsTestScreen(viewModel)
        }
    }

    private companion object {
        val REQUIRED_PERMISSIONS =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
    }
}

@Composable
fun NearbyConnectionsTestScreen(
    viewModel: MainViewModel
) {
    //val user = viewModel.userState.collectAsState()
    val opponent by viewModel.isConnection.collectAsState()
    val isComplete by viewModel.isComplete.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.size(10.dp))
        BusinessCard(data = myAccount, cardSize = "big")
        Spacer(Modifier.size(10.dp))
        if(isComplete) {
            myAccount.friendlist.add(opponent)
            LazyRow(
                modifier = Modifier
                    .height(210.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(myAccount.friendlist) { data ->
                    BusinessCard(data = data, cardSize = "big")
                }
            }
        }
        Button(
            onClick = {
                viewModel.startAdvertising()
                viewModel.startDiscovery()
            },
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(text = "start connecting")
        }
        Button(
            onClick = {
                viewModel.resetUser()
            },
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(text = "reset user")
        }
        Text(
            text = "isComplete: $isComplete",
        )
    }
}
