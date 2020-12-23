package com.example.unsplash.util

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat

object PermissionUtil {

    private const val PERMISSION_REQUEST_CODE = 1000

    fun checkPermission(activity: Activity, permissions: List<String>): Boolean {

        var needPermission = false

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(activity, permissions.toTypedArray(), PERMISSION_REQUEST_CODE)
                needPermission = true
            }
        }

        return needPermission

    }

}