package com.skyline_info_system.recordme.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "calls")
data class CallEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val callerNumber: String,
    val callStartTime: Date,
    val callDuration: Long,
    val callRecordingFilePath: String,
)
