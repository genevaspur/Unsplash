package com.example.unsplash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.vo.ImageVO

class ImageListAdapter constructor(val context: Context, val data: ArrayList<ImageVO>) : BaseAdapter() {


    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val itemRow = LayoutInflater.from(context).inflate(R.layout.image_row, parent, false)
        val imageView = itemRow.findViewById<ImageView>(R.id.imageView)
        val imageVO = data[position]

        Glide.with(context).load(imageVO.urls.small).into(imageView)

        return itemRow
    }

}



