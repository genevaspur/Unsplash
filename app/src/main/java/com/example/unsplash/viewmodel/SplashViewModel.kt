package com.example.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unsplash.common.exception.ExceptionHandler
import com.example.unsplash.repository.BaseRepository
import com.example.unsplash.repository.ISplashRepository
import com.example.unsplash.vo.VersionVO

class SplashViewModel (
    application: Application,
    private val splashRepository: ISplashRepository,
    exceptionHandler: ExceptionHandler
) : BaseViewModel(application,  exceptionHandler) {

    val updateState: LiveData<VersionVO> get() = _updateState
    private var _updateState = MutableLiveData<VersionVO>()

    val downloadState: LiveData<Int> get() = _downloadState
    private var _downloadState = MutableLiveData<Int>()

    fun checkUpdate() {
        launchMainCoroutine {
            BaseRepository.checkVersion(_updateState)
        }
    }

    fun updateApplication(force: Boolean) {

        if (force) {
            // 강제 업데이트
            downloadApk()


        } else {
            // 업데이트
            downloadApk()

        }

    }

    private fun downloadApk() {

        launchIoCoroutine {
            splashRepository.updateApplication(_downloadState)
        }

    }

}