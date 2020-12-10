package com.example.unsplash.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unsplash")
class UnsplashEntity(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                     @ColumnInfo(name = "url") var url: String,
                     @ColumnInfo(name = "profile_image") var profile_image: String?,
                     @ColumnInfo(name = "user_id") var user_id: String
) {
    constructor(): this(url =  "", profile_image = "", user_id = "")
}