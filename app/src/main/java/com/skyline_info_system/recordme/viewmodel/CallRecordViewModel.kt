package com.skyline_info_system.recordme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skyline_info_system.recordme.model.CallRecord
import com.skyline_info_system.recordme.repo.CallRepository

class CallRecordViewModel(private val callRepository: CallRepository) : ViewModel() {
    private val _callRecordSaved = MutableLiveData<Boolean>()
    val callRecordSaved: LiveData<Boolean> get() = _callRecordSaved

    fun saveCallRecord(callRecord: CallRecord) {
        callRepository.saveCallRecord(callRecord)
        _callRecordSaved.value = true
    }
}