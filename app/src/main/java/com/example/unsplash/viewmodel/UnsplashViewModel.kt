package com.example.unsplash.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.activity.client_id
import com.example.unsplash.adapter.ImageListAdapter
import com.example.unsplash.common.ListLiveData
import com.example.unsplash.database.entity.UnsplashEntity
import com.example.unsplash.repository.UnsplashRepository
import com.example.unsplash.retrofit.RetrofitClient
import com.example.unsplash.retrofit.RetrofitService
import com.example.unsplash.vo.ImageVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnsplashViewModel(
        application: Application,
        private val unsplashRepository: UnsplashRepository
) : BaseViewModel(application) {

    val imageData: LiveData<List<ImageVO>> get() = _imageData

    private var _imageData = ListLiveData<ImageVO, List<ImageVO>>()

    private lateinit var retrofitService : RetrofitService
    var pageNum = 1

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        retrofitService = RetrofitClient.getInstance().run {
            create(RetrofitService::class.java)
        }
    }

    fun getPhotoList(count: Int) {
        launchCoroutine {
            searchPhotoList(count)
        }
    }

    private fun searchPhotoList(count: Int) {

        retrofitService.requestRandomPhoto(client_id, count).enqueue(object : Callback<List<ImageVO>> {
            override fun onFailure(call: Call<List<ImageVO>>, t: Throwable) {
                Log.d("unsplash", "$t")
            }

            override fun onResponse(call: Call<List<ImageVO>>, response: Response<List<ImageVO>>) {
                val list = response.body()
                list ?: return
                _imageData.setElement(list)
            }
        })

    }

    fun selectDbAll() {
        CoroutineScope(Dispatchers.Main).launch {
            val dbData: LiveData<List<UnsplashEntity>> = unsplashRepository.getAll()
            dbData.value?.run {
                for (data in this) {
                    Log.i("unsplash", "DB : " + data.url)
                }
            }
        }
    }

    fun insertDb(view: View) {
        // FIXME: 12/9/20
        var unsplashEntity = UnsplashEntity(0, "abc", "def", "ghi")
        unsplashRepository.insert(unsplashEntity)

    }


}