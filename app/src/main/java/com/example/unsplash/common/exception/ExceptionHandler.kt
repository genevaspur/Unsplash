package com.example.unsplash.common.exception

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

open class ExceptionHandler {
    private var _exception = MutableLiveData<Throwable>()

    val exception: LiveData<Throwable>
        get() = _exception

    private val exceptionHandler: CoroutineExceptionHandler
        get() {
            return CoroutineExceptionHandler { coroutineContext, throwable ->
                throwable.printStackTrace()
                coroutineContext.cancel()
                _exception.value = throwable
            }
        }

    val mainScope = Dispatchers.Main + exceptionHandler
    val ioScope = Dispatchers.IO + exceptionHandler

}