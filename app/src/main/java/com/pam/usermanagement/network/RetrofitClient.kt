package com.pam.usermanagement.network

import com.pam.usermanagement.helper.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()