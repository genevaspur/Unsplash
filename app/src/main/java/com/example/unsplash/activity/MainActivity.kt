package com.example.unsplash.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.unsplash.R
import com.example.unsplash.adapter.ImageListAdapter
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

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRetrofit()

        listView = findViewById(R.id.listView)


        val context = this
        // IO or MAIN
        CoroutineScope(Dispatchers.IO).launch {

            getPhotoList(retrofitService, 10, context)

        }







    }

    private fun initRetrofit() {
        retrofit = RetrofitClient.getInstance()
        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    private fun getPhotoList(service: RetrofitService, count: Int, context: Context) {

        // TODO COUNT
        service.requestRandomPhoto(client_id, count).enqueue(object : Callback<ArrayList<ImageVO>> {

            override fun onFailure(call: Call<ArrayList<ImageVO>>, t: Throwable) {
                Log.d("TEST1234", "$t")
            }

            override fun onResponse(
                call: Call<ArrayList<ImageVO>>,
                response: Response<ArrayList<ImageVO>>
            ) {

                val list = response.body()
                if (list != null) {
                    // TODO
                    listView.adapter = ImageListAdapter(context, list)
                }

            }

        })

    }

}













