package com.example.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unsplash.common.exception.ExceptionHandler
import com.example.unsplash.repository.BaseRepository
import com.example.unsplash.repository.IUnsplashRepository
import com.example.unsplash.vo.VersionVO

class SplashViewModel (
    application: Application,
    private val unsplashRepository: IUnsplashRepository,
    exceptionHandler: ExceptionHandler
) : BaseViewModel(application,  exceptionHandler) {

    val updateState: LiveData<VersionVO> get() = _updateState

    private var _updateState = MutableLiveData<VersionVO>()

    fun checkUpdate() {
        launchMainCoroutine {
            BaseRepository.checkVersion(_updateState)
        }
    }

}