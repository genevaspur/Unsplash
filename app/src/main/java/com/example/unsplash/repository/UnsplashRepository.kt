package com.example.unsplash.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.unsplash.database.UnsplashDB
import com.example.unsplash.database.dao.UnsplashDao
import com.example.unsplash.database.entity.UnsplashEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UnsplashRepository(application: Application) {

    private val unsplashDB = UnsplashDB.getInstance(application)
    private val unsplashDao: UnsplashDao = unsplashDB.unsplashDao()

    suspend fun getAll(): LiveData<List<UnsplashEntity>> {
        lateinit var resultList: LiveData<List<UnsplashEntity>>
        CoroutineScope(Dispatchers.IO).launch {
            resultList = unsplashDao.getAll()
        }.join()
        return resultList
    }

    fun insert(unsplashEntity: UnsplashEntity) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                unsplashDao.insert(unsplashEntity)
            }
        } catch (e: Exception) {
            Log.i("unsplash", e.printStackTrace().toString())
        }
    }

    fun deleteAll() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                unsplashDao.deleteAll()
            }
        } catch (e: Exception) {
            Log.i("unsplash", e.printStackTrace().toString())
        }
    }

}
