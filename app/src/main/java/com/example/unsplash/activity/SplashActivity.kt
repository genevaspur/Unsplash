package com.example.unsplash.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.example.unsplash.BuildConfig
import com.example.unsplash.R
import com.example.unsplash.common.exception.ExceptionHandler
import com.example.unsplash.databinding.ActivitySplashBinding
import com.example.unsplash.repository.SplashRepository
import com.example.unsplash.util.DownloadNotificationUtil
import com.example.unsplash.viewmodel.SplashViewModel
import com.example.unsplash.vo.VersionVO
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BindingActivity<SplashViewModel, ActivitySplashBinding>() {

    private val downloadNotificationUtil by lazy {
        DownloadNotificationUtil(application)
    }

    override fun setContentId() = R.layout.activity_splash
    override fun bindViewModel() = SplashViewModel(application, SplashRepository(), ExceptionHandler(), getExternalFilesDir(null)!!)

    public override fun start() {
        super.start()

        vm.updateState.observe(this, {
            updateHandler(it)
        })

        vm.checkUpdate()

        vm.downloadState.observe(this, {
            showDownloadState(it)
        })

        // FIXME: 12/30/20 TEST
        button.setOnClickListener {
            Toast.makeText(this, "Pressed", Toast.LENGTH_SHORT).show()
        }

        vm.updateFile.observe(this, { file ->
            val intent = Intent(Intent.ACTION_VIEW)

            val uri: Uri
            //8.0 이상부터 파일 프로바이더 적용
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                uri = FileProvider.getUriForFile(
                    application.applicationContext,
                    BuildConfig.APPLICATION_ID +".fileprovider",
                    file)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                uri = Uri.fromFile(file)
            }

            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            intent.setDataAndType(uri, "application/vnd.android.package-archive") // TODO

            startActivity(intent)
            finish()
        })

    }

    private fun changeActivityWithDelay() {
        Handler().postDelayed({
            changeActivity(UnsplashActivity::class.java)
        }, 2000)

    }

    private fun updateHandler(vo: VersionVO) {
        if (vo.needUpdate) showUpdateAlert(vo.force, UnsplashActivity::class.java)
        else changeActivityWithDelay()
    }

    private fun showDownloadState(progress: Int) {
        downloadNotificationUtil.updateNotification(progress)
    }

    private fun <T : BaseActivity<*>> showUpdateAlert(force: Boolean, targetActivity: Class<T>) {

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

}