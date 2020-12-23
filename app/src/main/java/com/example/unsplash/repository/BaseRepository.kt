package com.example.unsplash.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unsplash.BuildConfig
import com.example.unsplash.retrofit.IVersionCheck
import com.example.unsplash.retrofit.RetrofitClient
import com.example.unsplash.util.saveToFile
import com.example.unsplash.vo.VersionVO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

interface IBaseRepository {
    suspend fun checkVersion(_updateData: MutableLiveData<VersionVO>)
    suspend fun updateApplication()
}

object BaseRepository : IBaseRepository {

    private val versionCheck = RetrofitClient.getInstance(BuildConfig.UPDATE_BASE_URL).run {
        create(IVersionCheck::class.java)
    }

    override suspend fun checkVersion(_updateData: MutableLiveData<VersionVO>) {

        val response = versionCheck.getVersion()
        if (!response.isSuccessful) throw Exception(response.code().toString())

        val versionVO: VersionVO? = response.body()

        versionVO?.run {
            needUpdate = versionCode > BuildConfig.VERSION_CODE
        }

        _updateData.postValue(versionVO)

    }

    override suspend fun updateApplication() {
        versionCheck.downloadApk().enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {

                    try {
                        val inputStream = response.body()?.byteStream()
                        // FIXME: 12/23/20
                        inputStream?.saveToFile("http://10.10.10.103:8080/aa.PNG")
                        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)


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
