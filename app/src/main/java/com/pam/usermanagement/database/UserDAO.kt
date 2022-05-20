package com.pam.usermanagement.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {
    @Query("SELECT * FROM databaseuser")
    suspend fun getUsers(): List<DatabaseUser>

    @Query("SELECT * FROM databaseuser WHERE login=:login")
    suspend fun getUser(login: String): DatabaseUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<DatabaseUser>)
}