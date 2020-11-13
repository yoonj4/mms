package com.yoonj4.mms

import android.content.Context
import android.telephony.SmsManager
import androidx.annotation.NonNull

class Mms(@NonNull private val context: Context) {
    private val smsManager: SmsManager = SmsManager.getDefault()

    fun sendVideo(@NonNull videoFilePath: String, @NonNull recipientNumbers: List<String>) {

        smsManager.sendMultimediaMessage(context)
        //TODO: figure out contentUri
    }
}