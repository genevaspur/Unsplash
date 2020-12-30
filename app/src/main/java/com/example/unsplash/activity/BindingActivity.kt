package com.example.unsplash.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import com.example.unsplash.BR
import com.example.unsplash.R
import com.example.unsplash.viewmodel.BaseViewModel

abstract class BindingActivity<VM: BaseViewModel, V: ViewDataBinding> : BaseActivity<V>() {

    protected lateinit var vm: VM

    abstract fun bindViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = bindViewModel()
        vm.error.observe(this, {handleThrowable(it)})
        binding.setVariable(BR.vm, vm)
        binding.lifecycleOwner = this
    }

    override fun start() {
        // TODO
    }

    private fun handleThrowable(throwable: Throwable) {
        showErrorAlert(throwable.toString())
    }

    protected fun AlertDialog.Builder.isCancelable(force: Boolean, block: () -> Unit): AlertDialog.Builder {
        if (!force) this.setNegativeButton(R.string.negative_btn) { _, _ -> block() }
        return this
    }

    protected fun <T: BaseActivity<*>> changeActivity(activity: Class<T>) {
        startActivity(Intent(this, activity))
        finish()
    }

}