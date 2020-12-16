package com.example.unsplash.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.unsplash.BR
import com.example.unsplash.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BindingActivity<VM: BaseViewModel, V: ViewDataBinding> : BaseActivity<V>() {

    protected lateinit var vm: VM

    abstract fun bindViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = bindViewModel()
        binding.setVariable(BR.vm, vm)
    }



}