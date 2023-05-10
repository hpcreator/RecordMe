package com.skyline_info_system.recordme.model

import java.util.Date

data class CallRecord(
    val callerNumber: String,
    val callStartTime: Date,
    val callDuration: Long,
    val callRecordingFilePath: String,
)