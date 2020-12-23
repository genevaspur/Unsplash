package com.example.unsplash.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.unsplash.R
import com.example.unsplash.common.exception.ExceptionHandler
import com.example.unsplash.databinding.ActivitySplashBinding
import com.example.unsplash.repository.UnsplashRepository
import com.example.unsplash.viewmodel.BaseViewModel
import com.example.unsplash.viewmodel.SplashViewModel
import com.example.unsplash.vo.VersionVO

class SplashActivity : BindingActivity<SplashViewModel, ActivitySplashBinding>() {

    override fun start() {

        vm.updateState.observe(this, {
            updateHandler(it)
        })

        vm.checkUpdate()

    }

    override fun bindViewModel() = SplashViewModel(application, UnsplashRepository(application), ExceptionHandler())

    override fun setContentId() = R.layout.activity_splash

    private fun changeActivityWithDelay() {
        Handler().postDelayed({
            changeActivity(UnsplashActivity::class.java)
        }, 2000)

    }

    private fun updateHandler(vo: VersionVO) {
        if (vo.needUpdate) showUpdateAlert(vo.force, UnsplashActivity::class.java)
        else changeActivityWithDelay()
    }

}