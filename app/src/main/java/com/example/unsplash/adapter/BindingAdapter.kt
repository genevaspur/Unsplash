package com.example.unsplash.adapter

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.vo.ImageVO

@BindingAdapter("listData")
fun setImageList(recyclerView: RecyclerView, list: LiveData<List<ImageVO>>?) {

    list?.value?.run {
        (recyclerView.adapter as? ImageListAdapter)?.addMoreItem(this)
    }
}