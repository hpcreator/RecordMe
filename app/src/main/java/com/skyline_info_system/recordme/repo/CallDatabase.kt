package com.skyline_info_system.recordme.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skyline_info_system.recordme.model.CallEntity

@Database(entities = [CallEntity::class], version = 1)
abstract class CallDatabase : RoomDatabase() {
    abstract fun callDao(): CallDao

    companion object {
        @Volatile
        private var INSTANCE: CallDatabase? = null

        fun getDatabase(context: Context): CallDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, CallDatabase::class.java, "CallRecorderDB"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}