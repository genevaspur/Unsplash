package com.example.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.unsplash.common.exception.ExceptionHandler
import com.example.unsplash.repository.BaseRepository
import com.example.unsplash.repository.IBaseRepository
import kotlinx.coroutines.*
import java.lang.Exception

abstract class BaseViewModel(
    application: Application,
    private val exceptionHandler: ExceptionHandler,
    private var updateViewModel: IBaseRepository = BaseRepository
) : AndroidViewModel(application) {

    private var job: Job? = null
    private var errorObserver: Observer<Throwable>

    private var _error = MutableLiveData<Throwable>()

    val error: LiveData<Throwable> get() = _error

    init {
        exceptionHandler.exception.observeForever(Observer<Throwable> {
            _error.value = it
        }.apply {
            errorObserver = this
        })
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancelChildren()
        job = null
        exceptionHandler.exception.removeObserver(errorObserver)
    }

    fun launchMainCoroutine(block: suspend() -> Unit) {
        job = CoroutineScope(exceptionHandler.mainScope).launch {
            launch { block() }.join()
        }
    }

    fun launchIoCoroutine(block: suspend() -> Unit) {
        job = CoroutineScope(exceptionHandler.ioScope).launch {
            launch { block() }.join()
        }
    }

    fun updateApplication(force: Boolean) {

        launchMainCoroutine {
            updateViewModel.updateApplication()
        }

        if (force) {
            // 강제 업데이트




        } else {
            // 업데이트


        }

    }

//    fun downloadApk() {
//        launchMainCoroutine {
//            baseRepository.updateApplication()
//        }
//    }

}