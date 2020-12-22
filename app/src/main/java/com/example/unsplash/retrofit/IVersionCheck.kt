package com.example.unsplash.retrofit

import com.example.unsplash.BuildConfig
import com.example.unsplash.vo.VersionVO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface IVersionCheck {

    @GET(BuildConfig.VERSION_CHECK)
    suspend fun getVersion(): Response<VersionVO>

    @GET("aa.PNG")
    suspend fun downloadApk(): Call<ResponseBody>

}