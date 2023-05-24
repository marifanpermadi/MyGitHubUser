package com.example.mygithubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavUser::class], version = 1)
abstract class FavUserDataBase : RoomDatabase() {

    abstract fun favDao(): FavDao

    companion object {
        @Volatile
        private var INSTANCE: FavUserDataBase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavUserDataBase {
            if (INSTANCE == null) {
                synchronized(FavUserDataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavUserDataBase::class.java, "fav_user_database"
                    )
                        .build()
                }
            }
            return INSTANCE as FavUserDataBase
        }
    }
}