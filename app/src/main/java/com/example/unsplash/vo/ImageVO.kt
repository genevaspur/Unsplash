package com.example.unsplash.vo

data class ImageVO (

    var id: String,
    var width: Int,
    var height: Int,
    var color: String,
    var downloads: Int,
    var likes: Int,
    var liked_by_user: Boolean,
    var description: String,
    var urls: ImageUrlVO

)