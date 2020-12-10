package com.example.unsplash.common

import androidx.lifecycle.LiveData

open class InitialLiveData<T>(value: T) : LiveData<T>() {

    var initialValue: T = value

    override fun getValue(): T {
        return super.getValue()!!
    }

    override fun setValue(value: T) {
        initialValue = value
        super.setValue(value)
    }

}