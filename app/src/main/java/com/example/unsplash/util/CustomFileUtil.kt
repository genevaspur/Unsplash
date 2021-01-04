package com.example.unsplash.util
import android.os.Environment
import okhttp3.ResponseBody
import java.io.File
import java.io.IOException
import java.io.InputStream


fun writeResponseBodyToDisk(responseBody: ResponseBody): Boolean {

    try {



        try {


            return true
        } catch (e: IOException) {
            return false
        }


    } catch (e: IOException) {
        return false
    }

}

fun InputStream.saveToFile(path: String) {
    File(path).outputStream().use { this.copyTo(it) }
}
