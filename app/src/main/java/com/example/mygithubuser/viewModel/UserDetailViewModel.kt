package com.example.mygithubuser.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.ItemsItem
import com.example.mygithubuser.UserDetailResponse
import com.example.mygithubuser.api.ApiConfig
import com.example.mygithubuser.database.FavUser
import com.example.mygithubuser.repository.FavUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : ViewModel() {

    private val mFavUserRepository: FavUserRepository = FavUserRepository(application)

    private val _userDetail = MutableLiveData<UserDetailResponse>()
    val userDetail: LiveData<UserDetailResponse> = _userDetail

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    init {
        getUserDetail()
    }

    fun getUserDetail(query: String = "") {
        Log.d(TAG, "getUserDetail called with query: $query")
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(query)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                Log.d(TAG, "userDetail response: $response")
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowers(query: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(query)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                    Log.d(TAG, "getUserDetail onResponse: ${response.body()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowings(query: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowings(query)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun insert(favUser: FavUser) {
        mFavUserRepository.insert(favUser)
    }

    fun delete(favUser: FavUser) {
        mFavUserRepository.delete(favUser)
    }

    fun checkIfUserExists(username: String): LiveData<Boolean> {
        return mFavUserRepository.checkIfUserExist(username)
    }

    companion object {
        const val TAG = "UserDetailViewModel"
    }

}