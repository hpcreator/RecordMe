package com.skyline_info_system.recordme.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import com.skyline_info_system.recordme.utils.Constants.ACTION_START_RECORDING
import com.skyline_info_system.recordme.utils.Constants.ACTION_STOP_RECORDING
import com.skyline_info_system.recordme.utils.appendLogToTrace

class CallRecorderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.appendLogToTrace("in onReceive: ${intent?.action}")
        when (intent?.action) {
            TelephonyManager.ACTION_PHONE_STATE_CHANGED -> {
                context?.appendLogToTrace("in ACTION_PHONE_STATE_CHANGED: ${intent.getStringExtra(TelephonyManager.EXTRA_STATE)}")
                when (intent.getStringExtra(TelephonyManager.EXTRA_STATE)) {
                    TelephonyManager.EXTRA_STATE_RINGING -> {
                        // Handle incoming call
                        context?.appendLogToTrace("in onReceive: State Ringing")
                    }

                    TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                        // Phone call started, start call recording
                        context?.appendLogToTrace("in onReceive: State Call Received")
                        val serviceIntent = Intent(context, CallRecorderService::class.java)
                        serviceIntent.action = ACTION_START_RECORDING
                        try {
                            ContextCompat.startForegroundService(context!!, serviceIntent)
                        } catch (e: Exception) {
                            context?.appendLogToTrace("fail to start service with Exception: ${e.message}")
                        }
                    }

                    TelephonyManager.EXTRA_STATE_IDLE -> {
                        // Phone call ended, stop call recording
                        context?.appendLogToTrace("in onReceive: State Call Ended")
                        val serviceIntent = Intent(context, CallRecorderService::class.java)
                        serviceIntent.action = ACTION_STOP_RECORDING
                        ContextCompat.startForegroundService(context!!, serviceIntent)
                    }

                    else -> {
                        context?.appendLogToTrace("in onReceive with action : ${intent.action}")
                    }
                }
            }
        }
    }
}