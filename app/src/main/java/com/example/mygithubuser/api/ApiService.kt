package com.example.mygithubuser.api

import com.example.mygithubuser.GitHubResponse
import com.example.mygithubuser.ItemsItem
import com.example.mygithubuser.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") query: String
    ): Call<GitHubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowings(
        @Path("username") username: String
    ): Call<List<ItemsItem>>


}