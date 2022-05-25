package com.pam.usermanagement.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {
    @Query("SELECT * FROM databaseuser")
    fun getUsers(): LiveData<List<DatabaseUser>>

    @Query("SELECT * FROM databaseuser WHERE login=:login")
    suspend fun getUser(login: String): DatabaseUser

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<DatabaseUser>)
}