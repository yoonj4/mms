package com.yoonj4.mms

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import android.telephony.SmsManager
import androidx.annotation.NonNull
import java.io.File
import java.lang.reflect.Method

class Mms(@NonNull private val context: Context) {
    private val smsManager: SmsManager = SmsManager.getDefault()

    fun sendVideo(@NonNull videoFilePath: String, @NonNull recipientNumbers: List<String>) {

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                val m: Method = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
                m.invoke(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val contentUri = Uri.fromFile(File(videoFilePath))
//        val contentUri = Builder()
//                .authority(context.packageName + ".fileprovider")
//                .path(videoFilePath)
//                .scheme(ContentResolver.SCHEME_CONTENT)
//                .build()
        recipientNumbers.forEach {
            smsManager.sendMultimediaMessage(context,
                    contentUri,
                    it,
                    null,
                    null)
        }
    }
}