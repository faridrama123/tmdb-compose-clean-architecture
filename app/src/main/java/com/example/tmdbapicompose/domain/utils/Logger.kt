package com.example.tmdbapicompose.domain.utils

import android.util.Log
import javax.inject.Inject

/**
 * This class defines the Logger
 */
class Logger @Inject constructor(){

    companion object{
        private const val TAG = "My Project Log"
        private const val LOG_ENABLE = true
        private const val DETAIL_ENABLE = true
    }

    private fun buildMsg(msg: String): String {
        val buffer = StringBuilder()
        if (DETAIL_ENABLE) {
            val stackTraceElement = Thread.currentThread().stackTrace[4]
            buffer.append("[ ")
            buffer.append(Thread.currentThread().name)
            buffer.append(": ")
            buffer.append(stackTraceElement.fileName)
            buffer.append(": ")
            buffer.append(stackTraceElement.lineNumber)
            buffer.append(": ")
            buffer.append(stackTraceElement.methodName)
        }
        buffer.append("() ] _____ ")
        buffer.append(msg)
        return buffer.toString()
    }


    fun d(msg: String) {
        if (LOG_ENABLE && Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, buildMsg(msg))
        }
    }

    fun i(msg: String) {
        if (LOG_ENABLE && Log.isLoggable(TAG, Log.INFO)) {
            Log.i(TAG, buildMsg(msg))
        }
    }

}