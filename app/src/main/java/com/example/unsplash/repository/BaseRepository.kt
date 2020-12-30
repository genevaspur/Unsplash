package com.example.unsplash.repository

import androidx.lifecycle.MutableLiveData
import com.example.unsplash.BuildConfig
import com.example.unsplash.retrofit.IVersionCheck
import com.example.unsplash.retrofit.RetrofitClient
import com.example.unsplash.vo.VersionVO
import java.lang.Exception

interface IBaseRepository {
    suspend fun checkVersion(_updateData: MutableLiveData<VersionVO>)
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

}
