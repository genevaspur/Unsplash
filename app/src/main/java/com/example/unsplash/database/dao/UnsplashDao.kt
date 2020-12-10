package com.example.unsplash.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.unsplash.database.entity.UnsplashEntity

@Dao
interface UnsplashDao {

    @Query("SELECT * FROM unsplash")
    fun getAll(): LiveData<List<UnsplashEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(unsplashEntity: UnsplashEntity)

    @Query("DELETE FROM unsplash")
    fun deleteAll()

}

