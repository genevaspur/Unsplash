package com.example.unsplash.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.R
import com.example.unsplash.adapter.ImageListAdapter
import com.example.unsplash.database.UnsplashDB
import com.example.unsplash.database.entity.UnsplashEntity
import com.example.unsplash.databinding.ActivityUnsplashBinding
import com.example.unsplash.listener.ImageScrollListener
import com.example.unsplash.repository.UnsplashRepository
import com.example.unsplash.retrofit.RetrofitClient
import com.example.unsplash.retrofit.RetrofitService
import com.example.unsplash.viewmodel.UnsplashViewModel
import com.example.unsplash.vo.ImageVO
import kotlinx.android.synthetic.main.activity_unsplash.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

//const val client_id = "2HtKs2HRLbBwxoN-weuaNbCxLZ3bn-ma3C2p_b-xKiI"  // 1
const val client_id = "RRhsjwbgzMem817xMG21l98m1P9Fj0z5c2UE3mNOFSw"  // 2


class MainActivity : BindingActivity<UnsplashViewModel, ActivityUnsplashBinding>() {

    private lateinit var rv: RecyclerView
    private lateinit var imageListAdapter: ImageListAdapter

    override fun setContentId() = R.layout.activity_unsplash
    override fun bindViewModel() = UnsplashViewModel(application, UnsplashRepository(application))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unsplash)

        rv = recyclerView
        val linearLayoutManager = LinearLayoutManager(this)
        rv.layoutManager = linearLayoutManager
        imageListAdapter = ImageListAdapter(this, linearLayoutManager, onLoadMoreListener)
        imageListAdapter.setRecyclerView(rv)

        imageListAdapter.setItemClickListener(object : ImageListAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                imageClickHandler(view, position)
            }
        })

        vm.getPhotoList(imageListAdapter, rv, 10)

        selectBtn.setOnClickListener {
            vm.selectDbAll()
        }

    }



    private val onLoadMoreListener = object:ImageScrollListener.OnLoadMoreListener {

        override fun onLoadMore() {
            vm.pageNum++
            // IO or MAIN
            CoroutineScope(Dispatchers.IO).launch {
                vm.getPhotoList(imageListAdapter, rv, 10)
            }
        }

    }

    private fun imageClickHandler(view: View, position: Int) = vm.insertDb(view)


}