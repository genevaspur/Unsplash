package com.example.unsplash.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ImageScrollListener(
        private val linearLayoutManager: LinearLayoutManager,
        private val onLoadMoreListener: OnLoadMoreListener?
) : RecyclerView.OnScrollListener() {

    var visibleThreshold = 5
    var visibleItemCount = 0
    var totalItemCount = 0
    var firstVisibleItem = 0
    var lastVisibleItem = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = linearLayoutManager.itemCount
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

        if ( (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold) ) {
            onLoadMoreListener?.onLoadMore()
        }

    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

}