package com.example.unsplash.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.unsplash.activity.client_id
import com.example.unsplash.common.ListLiveData
import com.example.unsplash.database.UnsplashDB
import com.example.unsplash.database.dao.UnsplashDao
import com.example.unsplash.database.entity.UnsplashEntity
import com.example.unsplash.retrofit.RetrofitClient
import com.example.unsplash.retrofit.RetrofitService
import com.example.unsplash.vo.ImageVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnsplashRepository(application: Application) {

    private val unsplashDB = UnsplashDB.getInstance(application)
    private val unsplashDao: UnsplashDao = unsplashDB.unsplashDao()

    private lateinit var retrofitService : RetrofitService

    fun getAll(): LiveData<List<UnsplashEntity>> {
        return unsplashDao.getAll()
    }

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        retrofitService = RetrofitClient.getInstance().run {
            create(RetrofitService::class.java)
        }
    }

    fun insert(unsplashEntity: UnsplashEntity) {
        unsplashDao.insert(unsplashEntity)
    }

    fun deleteAll() {
        unsplashDao.deleteAll()
    }

    fun searchPhotoList(count: Int, _imageData: ListLiveData<ImageVO, List<ImageVO>>) {

        retrofitService.requestRandomPhoto(client_id, count).enqueue(object : Callback<List<ImageVO>> {
            override fun onFailure(call: Call<List<ImageVO>>, t: Throwable) {
                Log.d("unsplash", "$t")
            }

            override fun onResponse(call: Call<List<ImageVO>>, response: Response<List<ImageVO>>) {
                val list = response.body()
                list ?: return
                _imageData.addElement(list)
            }
        })

    }

}
