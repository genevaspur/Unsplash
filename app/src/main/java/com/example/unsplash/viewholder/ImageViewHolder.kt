package com.example.unsplash.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.BR
import com.example.unsplash.R
import com.example.unsplash.vo.ImageVO

class ImageViewHolder(
        val viewDataBinding: ViewDataBinding
) : RecyclerView.ViewHolder(viewDataBinding.root) {

    fun <T: Any> bind(vo: T) {
        viewDataBinding.setVariable(BR.vo, vo)
    }

}