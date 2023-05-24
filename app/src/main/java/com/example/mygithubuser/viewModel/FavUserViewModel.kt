package com.example.mygithubuser.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.database.FavUser
import com.example.mygithubuser.repository.FavUserRepository

class FavUserViewModel(application: Application) : ViewModel() {
    private val mFavUserRepository: FavUserRepository = FavUserRepository(application)

    fun getAllFavUser(): LiveData<List<FavUser>> = mFavUserRepository.getAllFavUser()
}