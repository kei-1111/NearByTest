package com.example.nearbyconnectionstest

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.android.gms.nearby.connection.Strategy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

val myAccount = allCards.random()

class MainViewModel(private val connectionsClient: ConnectionsClient) : ViewModel() {

    private val _uerState = MutableStateFlow(CardsObj())
    val userState: StateFlow<CardsObj> = _uerState.asStateFlow()
    private val _isOpponentsState = MutableStateFlow(CardsObj())
    val isConnection: StateFlow<CardsObj> = _isOpponentsState.asStateFlow()
    private val _isComplete = MutableStateFlow(false)
    val isComplete: StateFlow<Boolean> = _isComplete.asStateFlow()

    private val nickName = "Keitaro"
    private val STRATEGY = Strategy.P2P_POINT_TO_POINT
    private val TAG = "NearbyConnectionsTest"
    private var remoteEndpointId = ""

    init{
        _uerState.value = myAccount
    }

    fun startAdvertising() {
        Log.d(TAG, "Start advertising...")
        val advertisingOptions = AdvertisingOptions.Builder().setStrategy(STRATEGY).build()
        connectionsClient.startAdvertising(
                nickName,
                SERVICE_ID,
                connectionLifecycleCallback,
                advertisingOptions
            )
            .addOnSuccessListener {
                // Advertise開始した
            }
            .addOnFailureListener {
                // Advertise開始できなかった
            }
    }

    fun startDiscovery() {
        Log.d(TAG, "Start discovering...")
        val discoveryOptions = DiscoveryOptions.Builder().setStrategy(STRATEGY).build()
        connectionsClient.startDiscovery(
                SERVICE_ID,
                endpointDiscoveryCallback,
                discoveryOptions
            )
            .addOnSuccessListener {
                // Discovery開始した
            }
            .addOnFailureListener {
                // Discovery開始できなかった
            }
    }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(
            endpointId: String,
            discoveredEndpointInfo: DiscoveredEndpointInfo
        ) {
            Log.d(TAG, "onEndpointFound")

            Log.d(TAG, "Requesting connection...")
            // Advertise側を発見した
            // とりあえず問答無用でコネクション要求してみる
            connectionsClient.requestConnection(
                nickName,
                endpointId,
                connectionLifecycleCallback
            )
        }

        override fun onEndpointLost(endpointId: String) {
            Log.d(TAG, "onEndpointLost")
            // 見つけたエンドポイントを見失った
        }
    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {

        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            Log.d(TAG, "onConnectionInitiated")

            Log.d(TAG, "Accepting connection...")
            // 他の端末からコネクションのリクエストを受け取った時
            connectionsClient.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            Log.d(TAG, "onConnectionResult")

            // コネクションリクエストの結果を受け取った時
            when (result.status.statusCode) {
                ConnectionsStatusCodes.STATUS_OK -> {
                    Log.d(TAG, "ConnectionsStatusCodes.STATUS_OK")
                    // コネクションが確立した。今後通信が可能。
                    // 通信時にはendpointIdが必要になるので、フィールドに保持する。
                    connectionsClient.stopAdvertising()
                    connectionsClient.stopDiscovery()
                    remoteEndpointId = endpointId
                    Log.d(TAG, "opponentEndpointId: $remoteEndpointId")
                    sendMyAccount(_uerState.value)
                }

                ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                }

                ConnectionsStatusCodes.STATUS_ERROR -> {
                }
            }
        }

        // コネクションが切断された時
        override fun onDisconnected(endpointId: String) {
            Log.d(TAG, "onDisconnected")
        }
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            if (payload.type == Payload.Type.BYTES) {
                Log.d(TAG, "onPayloadReceived")
                _isOpponentsState.update {
                    recieveAccount(payload)
                }
                _isComplete.update {
                    true
                }
            }
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
            // 転送状態が更新された時詳細は省略
        }
    }

    private fun sendMyAccount(user: CardsObj) {
        val payload = Payload.fromBytes(("${user.id},${user.avater},${user.name},${user.name_roma},${user.gender},${user.job},${user.birthday},${user.message}").toByteArray())
        connectionsClient.sendPayload(
            remoteEndpointId,
            payload
        )
    }

    private fun recieveAccount(payload: Payload): CardsObj{
        val recieveData = String(payload.asBytes()!!)
        val recieveDataList = recieveData.split(",")
        val recieveUser = CardsObj(
            id = recieveDataList[0],
            avater = null,
            name = recieveDataList[2],
            name_roma = recieveDataList[3],
            gender = recieveDataList[4],
            job = recieveDataList[5],
            birthday = recieveDataList[6],
            message = recieveDataList[7],
            mutableListOf()
        )
        return recieveUser
    }

    fun resetUser() {
        connectionsClient.stopAdvertising()
        connectionsClient.stopDiscovery()
        connectionsClient.stopAllEndpoints()
        _isComplete.update{
            false
        }
        Log.d(TAG, "${_uerState.value}")
    }

    private fun addList(user: CardsObj) {
        val friendList = _uerState.value.friendlist
        friendList.add(user)
        _uerState.update {
            it.copy(friendlist = friendList)
        }
    }
}


