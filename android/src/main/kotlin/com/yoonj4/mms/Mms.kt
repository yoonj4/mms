package com.yoonj4.mms

import android.content.ContentResolver
import android.content.Context
import android.net.Uri.Builder
import android.telephony.SmsManager
import androidx.annotation.NonNull

class Mms(@NonNull private val context: Context) {
    private val smsManager: SmsManager = SmsManager.getDefault()

    fun sendVideo(@NonNull videoFilePath: String, @NonNull recipientNumbers: List<String>) {

        val contentUri = Builder()
                        .authority(context.packageName + ".fileprovider")
                        .path(videoFilePath)
                        .scheme(ContentResolver.SCHEME_CONTENT)
                        .build()

        recipientNumbers.forEach {
            smsManager.sendMultimediaMessage(context,
                    contentUri,
                    it,
                    null,
                    null)
        }
    }
}