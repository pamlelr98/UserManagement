package com.pam.usermanagement.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseUser::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDAO
}

private lateinit var INSTANCE: UserDatabase

@Synchronized
fun getDatabase(context: Context): UserDatabase {
    if (!::INSTANCE.isInitialized) {
        INSTANCE = Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java, "databaseuser"
        ).build()
    }
    return INSTANCE
}