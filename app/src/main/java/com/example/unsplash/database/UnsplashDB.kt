package com.example.unsplash.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.unsplash.database.dao.UnsplashDao
import com.example.unsplash.database.entity.UnsplashEntity

@Database(entities = [UnsplashEntity::class], version = 1)
abstract class UnsplashDB : RoomDatabase() {

    abstract fun unsplashDao(): UnsplashDao

    companion object {

        private var INSTANCE: UnsplashDB? = null

//        fun getInstance(context: Context): UnsplashDB? {
//
//            if (INSTANCE == null) {
//                synchronized(UnsplashDB::class) {
//                    INSTANCE = Room.databaseBuilder(
//                            context.applicationContext,
//                            UnsplashDB::class.java,
//                            "unsplash.db"
//                    ).fallbackToDestructiveMigration().build()
//                }
//            }
//
//            return INSTANCE
//
//        }

        fun getInstance(context: Context): UnsplashDB = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                UnsplashDB::class.java,
                "unsplash.db"
            ).build().apply {
                INSTANCE = this
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }

}