package com.example.unsplash.repository

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unsplash.BuildConfig
import com.example.unsplash.retrofit.IVersionCheck
import com.example.unsplash.retrofit.RetrofitClient
import com.example.unsplash.util.saveToFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception
import java.lang.RuntimeException

interface ISplashRepository {
    suspend fun updateApplication(_downloadState: MutableLiveData<Int>, _updateFile: MutableLiveData<File>, fileDir: File)
}

class SplashRepository : ISplashRepository {

    private val versionCheck = RetrofitClient.getInstance(BuildConfig.UPDATE_BASE_URL).run {
        create(IVersionCheck::class.java)
    }

    override suspend fun updateApplication(_downloadState: MutableLiveData<Int>, _updateFile: MutableLiveData<File>, fileDir: File) {
        withContext(Dispatchers.IO) {
            //코루틴 내부에서 try catch 쓰지 않기 위해 변형
            //코루틴 외부로 exception 전달하여 exceptionhandler에서 처리하도록 수정

            val file = File("$fileDir/app-dev-debug.apk")
            if (file.exists()) file.delete()

            val response = versionCheck.downloadApk()

            if(!response.isSuccessful)    throw RuntimeException("통신오류")

            val body = response.body() ?: throw RuntimeException("데이터 없음")

            val inputStream = body.byteStream()
            inputStream.saveToFile("$fileDir/app-dev-debug.apk")
            _updateFile.postValue(file)
        }
    }

}
