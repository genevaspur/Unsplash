package com.example.unsplash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.listener.ImageScrollListener
import com.example.unsplash.viewholder.ImageViewHolder
import com.example.unsplash.vo.ImageVO
import kotlinx.android.synthetic.main.image_row.view.*

class ImageListAdapter(
        private val context: Context,
        private val linearLayoutManager: LinearLayoutManager,
        private val onLoadMoreListener: ImageScrollListener.OnLoadMoreListener
) : RecyclerView.Adapter<ImageViewHolder>() {

    var list = mutableListOf<ImageVO>()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_row, parent, false)
        return ImageViewHolder(view)
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

    }

    fun setRecyclerView(view: RecyclerView) {
        view.addOnScrollListener(ImageScrollListener(linearLayoutManager, onLoadMoreListener))
    }

    fun addMoreItem(newItem: List<ImageVO>) {
        list.addAll(newItem)
        notifyItemRangeChanged(0, list.size)
        ImageScrollListener.isLoading = false
    }

}



