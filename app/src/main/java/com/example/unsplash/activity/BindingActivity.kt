package com.example.unsplash.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

    protected fun <T: BaseActivity<*>> showUpdateAlert(force: Boolean, targetActivity: Class<T>) {

        val message: String = if (force) getString(R.string.msg_force_update)
        else getString(R.string.msg_update)

        AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(!force)
                .setPositiveButton(R.string.positive_btn) { _, _ -> vm.updateApplication(force) }
                .isCancelable(force) { changeActivity(targetActivity) }
                .create()
                .show()

    }

    private fun AlertDialog.Builder.isCancelable(force: Boolean, block: () -> Unit): AlertDialog.Builder {
        if (!force) this.setNegativeButton(R.string.negative_btn) { _, _ -> block() }
        return this
    }

    protected fun <T: BaseActivity<*>> changeActivity(activity: Class<T>) {
        startActivity(Intent(this, activity))
        finish()
    }

}