package com.example.unsplash.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VersionVO(

    @Expose @SerializedName("VERSION_CODE")
    var versionCode: Int,

    @Expose @SerializedName("VERSION_NAME")
    var versionName: String,

    @Expose @SerializedName("TITLE")
    var title: String,

    @Expose @SerializedName("MESSAGE")
    var message: String,

    @Expose @SerializedName("FORCE")
    var force: Boolean,

    @Expose @SerializedName("DOWNLOAD_URL")
    var downloadUrl: String,

    var needUpdate: Boolean

)