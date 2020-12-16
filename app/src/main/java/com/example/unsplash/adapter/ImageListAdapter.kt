package com.example.unsplash.adapter

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.R
import com.example.unsplash.listener.ImageScrollListener
import com.example.unsplash.vo.ImageVO

class ImageListAdapter(
        private val context: Context,
        private val linearLayoutManager: LinearLayoutManager,
        private val onLoadMoreListener: ImageScrollListener.OnLoadMoreListener
) : BaseImageListAdapter<ImageVO>(context, linearLayoutManager, R.layout.image_row) {

    fun setRecyclerView(view: RecyclerView) {
        view.addOnScrollListener(ImageScrollListener(linearLayoutManager, onLoadMoreListener))
    }

    override fun getDiffCallback() = object : DiffUtilCallback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].id == newData[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }
    }


}



