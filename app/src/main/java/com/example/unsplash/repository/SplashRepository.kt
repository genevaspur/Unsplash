package com.example.unsplash.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unsplash.BuildConfig
import com.example.unsplash.retrofit.IVersionCheck
import com.example.unsplash.retrofit.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

interface ISplashRepository {
    suspend fun updateApplication(_downloadState: MutableLiveData<Int>)
}

class SplashRepository() : ISplashRepository {

    private val versionCheck = RetrofitClient.getInstance(BuildConfig.UPDATE_BASE_URL).run {
        create(IVersionCheck::class.java)
    }

    override suspend fun updateApplication(_downloadState: MutableLiveData<Int>) {

        versionCheck.downloadApk().enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {

                    try {
                        val inputStream = response.body()?.byteStream()

                        response.body()?.let {

                            val byte = byteArrayOf((1024 * 4).toByte())
                            var total: Long = 0
                            inputStream?.read(byte)?.run {
                                while (this != -1) {

                                    total += this

                                    val progress = ((total * 100).toDouble() / it.contentLength().toDouble()).toInt()
                                    _downloadState.postValue(progress)

                                }
                            }

                        }

//                        inputStream?.saveToFile("http://10.10.10.103:8080/app-dev-debug.apk")
//                        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

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
