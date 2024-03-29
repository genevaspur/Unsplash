package com.example.unsplash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.listener.ImageScrollListener
import com.example.unsplash.viewholder.ImageViewHolder

abstract class BaseImageListAdapter<T: Any>(
        private val context: Context,
        private val layoutManager: RecyclerView.LayoutManager,
        private val layoutId: Int
) : RecyclerView.Adapter<ImageViewHolder>() {

    var list = mutableListOf<T>()

    private val diffUtilCallback: DiffUtilCallback by lazy {
        getDiffCallback()
    }

    abstract fun getDiffCallback(): DiffUtilCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) = holder.bind(list[position])

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun addMoreItem(newItem: List<T>) {

        diffUtilCallback.newData = newItem.toMutableList()
        diffUtilCallback.oldData = list

        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        list.clear()
        list.addAll(newItem)
        diffResult.dispatchUpdatesTo(this)
        ImageScrollListener.isLoading = false

    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

    }

    abstract inner class DiffUtilCallback : DiffUtil.Callback() {
        var oldData = mutableListOf<T>()
        var newData = mutableListOf<T>()
        override fun getOldListSize(): Int = oldData.size
        override fun getNewListSize(): Int = newData.size
    }

}