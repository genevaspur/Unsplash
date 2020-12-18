package com.example.unsplash.listener

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class ImageScrollListener(
        private val linearLayoutManager: LinearLayoutManager,
        private val onLoadMoreListener: OnLoadMoreListener?
) : RecyclerView.OnScrollListener() {

    companion object {
        var isLoading = false
    }

    private var visibleThreshold = 5
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var firstVisibleItem = 0
    private var lastVisibleItem = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = linearLayoutManager.itemCount
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

        if ( !isLoading && (lastVisibleItem + visibleThreshold) > totalItemCount ) {
            isLoading = true
            onLoadMoreListener?.onLoadMore()
        }

    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

}