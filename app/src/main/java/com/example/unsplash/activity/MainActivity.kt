package com.example.unsplash.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.unsplash.R
import com.example.unsplash.retrofit.RetrofitClient
import com.example.unsplash.retrofit.RetrofitService
import com.example.unsplash.vo.ImageVO
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

const val client_id = "2HtKs2HRLbBwxoN-weuaNbCxLZ3bn-ma3C2p_b-xKiI"

class MainActivity : BaseActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var retrofitService : RetrofitService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRetrofit()

        // IO or MAIN
        CoroutineScope(Dispatchers.IO).launch {

            getPhotoList(retrofitService, 10)

        }




    }

    private fun initRetrofit() {
        retrofit = RetrofitClient.getInstance()
        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    private fun getPhotoList(service: RetrofitService, count: Int) {

        // TODO COUNT
        service.requestRandomPhoto(client_id, count).enqueue(object : Callback<ArrayList<ImageVO>> {

            override fun onFailure(call: Call<ArrayList<ImageVO>>, t: Throwable) {
                Log.d("TEST1234", "$t")
            }

            override fun onResponse(
                call: Call<ArrayList<ImageVO>>,
                response: Response<ArrayList<ImageVO>>
            ) {

                var list = response.body()
                if (list != null) {
                    for (a in list) {
                        Log.d("TEST1234", a.toString())
                    }
                }

            }

        })

    }

}













