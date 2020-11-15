package com.yoonj4.mms

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity


class Mms(@NonNull private val context: Context) {

    fun sendVideo(@NonNull videoFilePath: String, @NonNull recipientNumbers: List<String>) {

        val contentUri = Uri.parse("file://$videoFilePath")
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