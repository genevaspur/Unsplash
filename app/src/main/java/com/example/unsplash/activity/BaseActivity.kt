package com.example.unsplash.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.unsplash.BuildConfig
import com.example.unsplash.R
import com.example.unsplash.util.PermissionUtil
import com.example.unsplash.util.PreferenceUtil
import kotlin.system.exitProcess

abstract class BaseActivity<V: ViewDataBinding> : AppCompatActivity() {

    companion object {
        private val PERMISSIONS = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        lateinit var prefs: PreferenceUtil

        private const val PREF_PERMISSION = "PERMISSION"
    }

    protected lateinit var binding: V

    private var needPermission = true

    protected abstract fun start()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, setContentId())
        binding.lifecycleOwner = this
        prefs = PreferenceUtil(this)

        needPermission = PermissionUtil.checkPermission(this, PERMISSIONS)

    }

    // TODO START -> ?
    override fun onStart() {
        super.onStart()
        if (!needPermission) start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var idx = 0
        var permissionGranted = true
        grantResults.forEach { result ->

            if (result != PackageManager.PERMISSION_GRANTED) {
                val requestPermission: Boolean = shouldShowRequestPermissionRationale(permissions[idx])
                if (!prefs.getBoolean(PREF_PERMISSION)) prefs.setBoolean(PREF_PERMISSION, requestPermission)
                idx++
                permissionGranted = false

                if (!requestPermission && prefs.getBoolean(PREF_PERMISSION)) {
                    showPermissionPermanentlyDeniedAlert()
                    return
                }

                showPermissionDeniedAlert()
                return
            }

        }

        if (permissionGranted) start()

    }

    private fun showPermissionPermanentlyDeniedAlert() {
        AlertDialog.Builder(this)
            .setMessage(R.string.permission_denied)
            .setCancelable(true)
            .setPositiveButton(R.string.terminate) { _, _ -> terminateApp()}
            .setNegativeButton(R.string.open_settings) { _, _ -> openSettings()}
            .create()
            .show()
    }

    private fun showPermissionDeniedAlert() {
        AlertDialog.Builder(this)
            .setMessage(R.string.permission_denied)
            .setCancelable(true)
            .setPositiveButton(R.string.terminate) { _, _ -> terminateApp() }
            .setNegativeButton(R.string.msg_request_permission) { _, _ -> PermissionUtil.checkPermission(this, PERMISSIONS)}
            .create()
            .show()
    }

    protected abstract fun setContentId(): Int

    protected fun openSettings() {
        startActivity(Intent(
            android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${BuildConfig.APPLICATION_ID}")
        ))
    }

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