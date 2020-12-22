package com.example.unsplash.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import com.example.unsplash.BR
import com.example.unsplash.R
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
        vm.error.observe(this, {handleThrowable(it)})
        binding.setVariable(BR.vm, vm)
        binding.lifecycleOwner = this
    }

    private fun handleThrowable(throwable: Throwable) {
        showErrorAlert(throwable.toString())
    }

    protected fun <T: Any> showUpdateAlert(force: Boolean, targetActivity: Class<T>) {

        val message: String
        val cancelable: Boolean

        if (force) {
            message = getString(R.string.msg_force_update)
            cancelable = false
        } else {
            message = getString(R.string.msg_update)
            cancelable = true
        }

        val updateDialog = AlertDialog.Builder(this)
            .setMessage(message)
            .setCancelable(cancelable)
            .setPositiveButton(R.string.positive_btn) { _, _ ->
                vm.updateApplication(force)
            }

        if (cancelable) updateDialog.setNegativeButton(R.string.negative_btn) { _, _ ->
            changeActivity(targetActivity)
        }

        updateDialog.create().show()

    }

    protected fun <T: Any> changeActivity(activity: Class<T>) {
        startActivity(Intent(this, activity))
        finish()
    }

}