package com.example.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*

abstract class BaseViewModel(
        application: Application
) : AndroidViewModel(application) {

    private var job: Job? = null

    init {
        // TODO
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancelChildren()
        job = null
    }

    fun launchCoroutine(block: suspend() -> Unit) {
        job = CoroutineScope(Dispatchers.Main).launch {
            launch { block() }.join()
        }
    }

}