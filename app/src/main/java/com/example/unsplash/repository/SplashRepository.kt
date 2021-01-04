package com.example.unsplash.repository

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unsplash.BuildConfig
import com.example.unsplash.retrofit.IVersionCheck
import com.example.unsplash.retrofit.RetrofitClient
import com.example.unsplash.util.saveToFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception

interface ISplashRepository {
    suspend fun updateApplication(_downloadState: MutableLiveData<Int>, _updateFile: MutableLiveData<File>, fileDir: File)
}

class SplashRepository(application: Application) : ISplashRepository {

    private val versionCheck = RetrofitClient.getInstance(BuildConfig.UPDATE_BASE_URL).run {
        create(IVersionCheck::class.java)
    }

    override suspend fun updateApplication(_downloadState: MutableLiveData<Int>, _updateFile: MutableLiveData<File>, fileDir: File) {

        val file = File("$fileDir/app-dev-debug.apk")
        if (file.exists()) file.delete()

        versionCheck.downloadApk().enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {

                    try {

                        // TODO Progress
                        val totalSize = response.body()?.contentLength() ?: 0
                        val inputStream = response.body()?.byteStream()
                        CoroutineScope(Dispatchers.IO).launch {
                            launch {
                                inputStream?.saveToFile("$fileDir/app-dev-debug.apk")
                            }.join()
                        }

                        _updateFile.postValue(file)

                    } catch (e: Exception) {
                        Log.i("unsplash", e.printStackTrace().toString())
                    }


                } else {
                    Log.d("unsplash", "SERVER_CONTACT_FAILED")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("unsplash", "$t")
            }

        })
    }

}
