package com.example.unsplash.retrofit

import com.example.unsplash.vo.ImageVO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("/photos/random")
    fun requestRandomPhoto(
        @Query("client_id") client_id: String,
        @Query("count") count: Int
    ) : Call<ArrayList<ImageVO>>


}