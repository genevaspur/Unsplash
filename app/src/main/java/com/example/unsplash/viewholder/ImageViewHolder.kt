package com.example.unsplash.viewholder

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

            T is ImageVO -> {
                val imageVO = T as ImageVO
                Glide.with(itemView)
                        .load(imageVO.urls.regular)
                        .into(imageView)

                userName.text = imageVO.user.name
            }

        }
    }

}