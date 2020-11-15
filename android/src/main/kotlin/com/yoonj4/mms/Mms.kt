package com.yoonj4.mms

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import java.io.File
import java.lang.reflect.Method

class Mms(@NonNull private val context: Context) {

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
        recipientNumbers.forEach {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.putExtra("address", it)
            sendIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
            sendIntent.flags = FLAG_ACTIVITY_NEW_TASK
            startActivity(context, sendIntent, null)
        }
//        val contentUri = Builder()
//                        .authority(context.packageName + ".fileprovider")
//                        .path(videoFilePath)
//                        .scheme(ContentResolver.SCHEME_CONTENT)
//                        .build()
    }
}