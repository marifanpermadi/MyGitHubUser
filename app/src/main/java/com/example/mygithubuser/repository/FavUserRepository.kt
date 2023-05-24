package com.example.mygithubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mygithubuser.database.FavDao
import com.example.mygithubuser.database.FavUser
import com.example.mygithubuser.database.FavUserDataBase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {

    private val mFavUserDao: FavDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavUserDataBase.getDatabase(application)
        mFavUserDao = db.favDao()
    }

    fun getAllFavUser(): LiveData<List<FavUser>> = mFavUserDao.getAllFavUser()

    fun insert(favUser: FavUser) {
        executorService.execute { mFavUserDao.insert(favUser) }
    }

    fun delete(favUser: FavUser) {
        executorService.execute { mFavUserDao.delete(favUser) }
    }

    fun checkIfUserExist(username: String): LiveData<Boolean> {
        return mFavUserDao.checkIfUserExists(username)
    }
}