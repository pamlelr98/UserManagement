package com.pam.usermanagement.network

import androidx.lifecycle.LiveData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com"
const val TOKEN = "bearer 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"

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

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

class RetrofitClient {
    companion object {
        private var instance: RetrofitApi? = null

        @Synchronized
        fun getInstance(): RetrofitApi {
            if (instance == null) {
                instance = Retrofit
                    .Builder()
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl(BASE_URL)
                    .build().create(RetrofitApi::class.java)
            }
            return instance as RetrofitApi
        }
    }
}


