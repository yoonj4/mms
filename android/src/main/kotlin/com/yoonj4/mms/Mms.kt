package com.yoonj4.mms

import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.net.Uri.Builder
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.annotation.NonNull
import com.yoonj4.mms.mmslib.ContentType
import com.yoonj4.mms.mmslib.InvalidHeaderValueException
import com.yoonj4.mms.mmslib.pdu.*
import io.flutter.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.math.abs


class Mms(@NonNull private val context: Context) {
    private val defaultExpiryTime = 7 * 24 * 60 * 60.toLong()
    private val defaultPriority = PduHeaders.PRIORITY_NORMAL

    private val mRandom: Random = Random()

    fun sendVideo(@NonNull videoFilePath: String, @NonNull recipientNumbers: List<String>) {

//        if (Build.VERSION.SDK_INT >= 24) {
//            try {
//                val m: Method = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
//                m.invoke(null)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//        val contentUri = Uri.fromFile(File(videoFilePath))
        val fileName = "send." + abs(mRandom.nextLong()).toString() + ".dat"
        val mSendFile = File(context.cacheDir, fileName)

        val pdu = buildPdu(context, recipientNumbers, videoFilePath)
        val writerUri: Uri = Builder()
                .authority("com.yoonj4.mms.MmsFileProvider")
                .path(fileName)
                .scheme(ContentResolver.SCHEME_CONTENT)
                .build()
        var writer: FileOutputStream? = null
        var contentUri: Uri? = null
        try {
            writer = FileOutputStream(mSendFile)
            writer.write(pdu)
            contentUri = writerUri
        } catch (e: IOException) {
            Log.e(TAG, "Error writing send file", e)
        } finally {
            if (writer != null) {
                try {
                    writer.close()
                } catch (e: IOException) {
                    Log.e(TAG, "Error closing file writer", e)
                }
            }
        }

        if (contentUri != null) {
            SmsManager.getDefault().sendMultimediaMessage(context,
                    contentUri, null /*locationUrl*/, null /*configOverrides*/,
                    null)
        } else {
            Log.e(TAG, "Error writing sending Mms")
        }
    }

    private fun buildPdu(context: Context, recipients: List<String>, videoFilePath: String): ByteArray? {
        val req = SendReq()
        // From, per spec
        val lineNumber = getSimNumber(context)
        if (!TextUtils.isEmpty(lineNumber)) {
            req.from = EncodedStringValue(lineNumber)
        }
        // To
        val encodedNumbers = EncodedStringValue.encodeStrings(recipients.toTypedArray())
        if (encodedNumbers != null) {
            req.to = encodedNumbers
        }
        // Date
        req.date = System.currentTimeMillis() / 1000
        // Body
        val body = PduBody()
        val size = addVideoPart(body, videoFilePath)
        Log.d(TAG, "PDU body size: $size")
        req.body = body
        // Message size
        req.messageSize = size.toLong()
        // Message class
        req.messageClass = PduHeaders.MESSAGE_CLASS_PERSONAL_STR.toByteArray()
        // Expiry
        req.expiry = defaultExpiryTime
        try {
            // Priority
            req.priority = defaultPriority
            // Delivery report
            req.deliveryReport = PduHeaders.VALUE_NO
            // Read report
            req.readReport = PduHeaders.VALUE_NO
        } catch (e: InvalidHeaderValueException) {
            Log.e(TAG, "priority, deliveryReport, or readReport has an invalid value", e)
        }
        return PduComposer(context, req).make()
    }

    private fun addVideoPart(pb: PduBody, videoFilePath: String): Int {
        val part = PduPart()
        // Set Content-Type.
        part.contentType = ContentType.VIDEO_MP4.toByteArray()
        part.data = File(videoFilePath).readBytes()
        part.filename = videoFilePath.substringAfterLast('/').toByteArray()
        pb.addPart(part)
        return part.data.size
    }

    private fun getSimNumber(context: Context): String? {
        val telephonyManager: TelephonyManager = context.getSystemService(
                Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.getLine1Number()
    }
}