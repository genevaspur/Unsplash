package com.example.unsplash.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.vo.ImageVO

@BindingAdapter("listData")
fun setImageList(recyclerView: RecyclerView, list: LiveData<List<ImageVO>>) {
    list.value?.run {
        (recyclerView.adapter as? ImageListAdapter)?.addMoreItem(this)
    }
}

@BindingAdapter("glideImage")
fun setGlideImage(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}