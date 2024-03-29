package com.example.unsplash.retrofit

import com.example.unsplash.BuildConfig
import com.example.unsplash.vo.VersionVO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Streaming

interface IVersionCheck {

    @GET(BuildConfig.VERSION_CHECK)
    suspend fun getVersion(): Response<VersionVO>

    @GET("app-dev-debug.apk")
//    @Headers("Accept-Encoding: *", "Content-Type: font/ttf")
//    @GET("test.ipa")
    @Streaming
    suspend fun downloadApk(): Response<ResponseBody>

}