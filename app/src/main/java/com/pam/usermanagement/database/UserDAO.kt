package com.pam.usermanagement.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pam.usermanagement.models.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM `Users`")
    fun getUsers(): LiveData<List<DatabaseUser>>

    @Query("SELECT * FROM `Users` WHERE login=:login")
    fun getUser(login: String): LiveData<DatabaseUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<DatabaseUser>)

    @Query("UPDATE `Users` SET name=:name, bio=:bio, blog=:blog, created_at=:created_at, followers=:followers, email=:email, location=:location WHERE login =:login")
    suspend fun updateUserInfo(
        name: String?,
        bio: String?,
        blog: String,
        created_at: String,
        followers: Int,
        email: String?,
        location: String?,
        login:String
    )
}