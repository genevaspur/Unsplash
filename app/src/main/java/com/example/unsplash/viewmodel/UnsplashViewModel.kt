package com.example.unsplash.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import com.example.unsplash.common.ListLiveData
import com.example.unsplash.common.exception.ExceptionHandler
import com.example.unsplash.database.entity.UnsplashEntity
import com.example.unsplash.repository.IUnsplashRepository
import com.example.unsplash.repository.UnsplashRepository
import com.example.unsplash.vo.ImageVO
import java.lang.RuntimeException

class UnsplashViewModel(
    application: Application,
    private val unsplashRepository: IUnsplashRepository,
    exceptionHandler: ExceptionHandler
) : BaseViewModel(application, exceptionHandler) {

    val imageData: LiveData<List<ImageVO>> get() = _imageData

    private var _imageData = ListLiveData<ImageVO, List<ImageVO>>()

    var pageNum = 1

    fun getPhotoList(count: Int) {
        launchMainCoroutine {
            unsplashRepository.searchPhotoList(count, _imageData)
        }
    }

    fun selectDbAll() {
        launchIoCoroutine {
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
        launchIoCoroutine {
            unsplashRepository.insert(unsplashEntity)
        }

    }


}