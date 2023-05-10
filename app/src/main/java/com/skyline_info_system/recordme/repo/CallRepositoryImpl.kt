package com.skyline_info_system.recordme.repo

import com.skyline_info_system.recordme.model.CallEntity
import com.skyline_info_system.recordme.model.CallRecord

class CallRepositoryImpl(private val callDao: CallDao) : CallRepository {
    override fun saveCallRecord(callRecord: CallRecord) {
        val callEntity = CallEntity(
            callerNumber = callRecord.callerNumber,
            callStartTime = callRecord.callStartTime,
            callDuration = callRecord.callDuration,
            callRecordingFilePath = callRecord.callRecordingFilePath
        )
        callDao.saveCall(callEntity)
    }
}
