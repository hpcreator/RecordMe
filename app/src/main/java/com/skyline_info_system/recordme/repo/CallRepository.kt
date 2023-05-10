package com.skyline_info_system.recordme.repo

import com.skyline_info_system.recordme.model.CallRecord

interface CallRepository {
    fun saveCallRecord(callRecord: CallRecord)
}