package com.example.unsplash.viewholder

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.vo.ImageVO

class ImageViewHolder(
        itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.imageView)

    fun bind(imageVO: ImageVO) {
        Glide.with(itemView)
            .load(imageVO.urls.regular)
            .into(imageView)
    }

}