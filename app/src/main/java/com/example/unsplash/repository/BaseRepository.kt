package com.example.unsplash.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unsplash.BuildConfig
import com.example.unsplash.activity.client_id
import com.example.unsplash.common.ListLiveData
import com.example.unsplash.database.UnsplashDB
import com.example.unsplash.database.dao.UnsplashDao
import com.example.unsplash.database.entity.UnsplashEntity
import com.example.unsplash.retrofit.IVersionCheck
import com.example.unsplash.retrofit.RetrofitClient
import com.example.unsplash.retrofit.RetrofitService
import com.example.unsplash.vo.ImageVO
import com.example.unsplash.vo.VersionVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
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
        val response = versionCheck.downloadApk()
        Log.i("unsplash", response.toString())
    }

}
