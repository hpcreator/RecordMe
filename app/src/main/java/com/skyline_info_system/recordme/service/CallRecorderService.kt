package com.skyline_info_system.recordme.service

import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import com.skyline_info_system.recordme.model.CallRecord
import com.skyline_info_system.recordme.repo.CallDatabase
import com.skyline_info_system.recordme.repo.CallRepositoryImpl
import com.skyline_info_system.recordme.utils.Constants.ACTION_START_RECORDING
import com.skyline_info_system.recordme.utils.Constants.ACTION_STOP_RECORDING
import com.skyline_info_system.recordme.utils.appendLogToTrace
import com.skyline_info_system.recordme.viewmodel.CallRecordViewModel
import java.util.Date

@Suppress("DEPRECATION")
class CallRecorderService : Service() {
    private lateinit var mediaRecorder: MediaRecorder
    private var isRecording = false
    private var callStartTime: Date? = null

    override fun onCreate() {
        super.onCreate()
        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(this)
        } else {
            MediaRecorder()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        appendLogToTrace("in onStartCommand with action : $action")
        if (action == ACTION_START_RECORDING) {
            startRecording()
        } else if (action == ACTION_STOP_RECORDING) {
            stopRecording()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startRecording() {
        appendLogToTrace("in StartRecording Method")
        if (isRecording) {
            return
        }

        mediaRecorder.apply {
            setAudioSource(MediaRecorder.AudioSource.VOICE_CALL)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(getOutputFilePath())
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
                mediaRecorder.start()
            } catch (e: Exception) {
                e.printStackTrace()
                appendLogToTrace("in StartRecording Exception throws as : ${e.message}")
            }
        }
    }

    private fun stopRecording() {
        appendLogToTrace("in stopRecording method")
        if (!isRecording) {
            return
        }

        try {
            mediaRecorder.stop()
            mediaRecorder.reset()
            mediaRecorder.release()
            isRecording = false

            // Calculate call duration
            val callDuration = Date().time - (callStartTime?.time ?: 0L)

            // Save call record to ViewModel for further processing
            val callRecord = CallRecord(
                callerNumber = getCallerNumber(),
                callStartTime = callStartTime ?: Date(),
                callDuration = callDuration,
                callRecordingFilePath = getOutputFilePath()
            )
            val callRecordViewModel =
                CallRecordViewModel(
                    callRepository = CallRepositoryImpl(
                        callDao = CallDatabase.getDatabase(applicationContext).callDao()
                    )
                ) // Provide your CallRepository implementation here
            callRecordViewModel.saveCallRecord(callRecord)

        } catch (e: Exception) {
            e.printStackTrace()
            appendLogToTrace("in stopRecording Exception throws as : ${e.message}")
        }

        stopSelf()
    }

    private fun getCallerNumber(): String {
        // Return the caller number
        // You can implement your own logic to get the caller number
        return "Unknown Caller"
    }

    private fun getOutputFilePath(): String {
        // Return the file path where the recorded call will be saved
        // You can customize the file name and directory as per your requirements
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
        return "$root/${System.currentTimeMillis()}.3gpp"
    }

    override fun onBind(intent: Intent): IBinder? = null
}