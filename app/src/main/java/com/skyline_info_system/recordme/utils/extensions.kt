package com.skyline_info_system.recordme.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

fun createLogTrashFile() {
    val logFileName = "RecordMe_LOGS.txt"
    val rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path
    val filePath = File(rootPath, logFileName)
    if (!filePath.exists()) {
        filePath.createNewFile()
        Log.e("TAG", "createLogTrashFile: File created at :$filePath")
    } else {
        Log.e("TAG", "createLogTrashFile: File already exist at $filePath")
    }
}

fun Context.appendLogToTrace(logMessage: String) {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS aa ZZZZZ", Locale.getDefault())
    val message =
        "\n${this.javaClass.simpleName} :: ${formatter.format(System.currentTimeMillis())} :: LogMessage ---->$logMessage\n\n"
    if (getLogTraceFile() != null && getLogTraceFile()!!.exists()) {
        try {
            getLogTraceFile()?.appendText(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } else {
        createLogTrashFile()
        appendLogToTrace(logMessage)
    }
}

private fun getLogTraceFile(): File? {
    val file = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path}/RecordMe_LOGS.txt")
    return if (file.exists()) {
        file
    } else {
        null
    }
}