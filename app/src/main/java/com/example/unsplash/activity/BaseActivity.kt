package com.example.unsplash.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.example.unsplash.R
import com.example.unsplash.util.PermissionUtil
import kotlin.system.exitProcess

abstract class BaseActivity<V: ViewDataBinding> : AppCompatActivity() {

    companion object {
        private val PERMISSIONS = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    protected lateinit var binding: V

    protected abstract fun start()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, setContentId())
        binding.lifecycleOwner = this

        val needPermission = PermissionUtil.checkPermission(this, PERMISSIONS)
        if (!needPermission) start()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        grantResults.forEach {
            if (it != PackageManager.PERMISSION_GRANTED) {
                showPermissionDeniedAlert()
                return
            }
        }

    }

    protected fun showPermissionDeniedAlert() {
        AlertDialog.Builder(this)
            .setMessage(R.string.permission_denied)
            .setCancelable(false)
            .setPositiveButton(R.string.terminate_app) { _, _ -> terminateApp() }
            .create()
            .show()
    }

    protected abstract fun setContentId(): Int

    protected fun terminateApp() {
        finishAffinity()
        System.runFinalization()
        exitProcess(0)
    }

    protected fun showErrorAlert(msg: String) {
        val dialog = AlertDialog.Builder(this)
                                .setMessage(msg)
                                .setCancelable(false)
                                .setPositiveButton(R.string.positive_btn, null)
                                .create()
        dialog.show()
    }

}