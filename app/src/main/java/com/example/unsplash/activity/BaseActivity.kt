package com.example.unsplash.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.unsplash.R

abstract class BaseActivity<V: ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, setContentId())
        binding.lifecycleOwner = this
    }

    protected abstract fun setContentId(): Int

    protected fun showErrorAlert(msg: String) {
        val dialog = AlertDialog.Builder(this)
                                .setMessage(msg)
                                .setCancelable(false)
                                .setPositiveButton(R.string.positive_btn, null)
                                .create()
        dialog.show()
    }

}