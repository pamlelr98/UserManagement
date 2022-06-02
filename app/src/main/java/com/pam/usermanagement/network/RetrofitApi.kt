package com.pam.usermanagement.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {
    @GET("users")
    suspend fun getUsers(@Header("Authorization") token: String): List<NetworkUser>

    @GET("users")
    suspend fun getPageUsers(
        @Header("Authorization") token: String,
        @Query("per_page") perPage: Int,
        @Query("since") since: Int
    ): List<NetworkUser>

    @GET("users/{username}")
    suspend fun getUserInfo(
        @Header("Authorization") token: String,
        @Path("username") username: String,
    ): NetworkUserInfo
}




