package com.skyline_info_system.recordme.repo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.skyline_info_system.recordme.model.CallEntity

@Dao
interface CallDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCall(callEntity: CallEntity)
}