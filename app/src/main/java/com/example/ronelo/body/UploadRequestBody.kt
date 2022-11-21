package com.example.ronelo.body

import android.os.Looper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream

class UploadRequestBody  (
    private val file: File,
    private val contentType: String,
    private val upCallback: UploadCallback
    ):RequestBody(){
    override fun contentType() = "$contentType/*".toMediaTypeOrNull()

    override fun writeTo(sink: BufferedSink) {
        val lengthFile = file.length()
        val bufferSize = ByteArray(BUFFER_SIZE)
        val fileInputStream = FileInputStream(file)
        var uploaded = 0L
        fileInputStream.use { inputStream ->
            var read: Int
            val handler = android.os.Handler(Looper.getMainLooper())
            while (inputStream.read(bufferSize).also {
                    read = it
            } != -1)
            {
                handler.post(ProgressUpdater(uploaded, lengthFile))
                uploaded += read
                sink.write(bufferSize, 0, read)
            }
        }
    }

    inner class ProgressUpdater(private  var uploaded: Long, private  var length: Long) : Runnable {
        override fun run() {
            upCallback.onProgressUpdate((100 * uploaded/length).toInt())
        }
    }

    interface UploadCallback{
        fun onProgressUpdate(percentage: Int)
    }

    companion object{
        private const val BUFFER_SIZE = 4000
    }

}
