package com.example.unsplash.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.vo.ImageVO

class ImageViewHolder(
        itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private val userName: TextView = itemView.findViewById(R.id.userName)

    fun bind(T: Any) {
        when(T) {
            is ImageVO -> {
                Glide.with(itemView)
                        .load(T.urls.regular)
                        .into(imageView)
                userName.text = T.user.name
            }

        }
    }

}