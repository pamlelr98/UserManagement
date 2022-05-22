package com.pam.usermanagement.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pam.usermanagement.database.UserDatabase
import com.pam.usermanagement.database.asUserModel
import com.pam.usermanagement.models.User
import com.pam.usermanagement.network.RetrofitClient
import com.pam.usermanagement.network.asUserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val database: UserDatabase) {
    val videos: LiveData<List<User>> = Transformations.map(
        database.userDao.getUsers()
    ) {
        it.asUserModel()
    }

    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {
            val users = RetrofitClient.getInstance().getPageUsers(50,46)
            database.userDao.insertAll(users.asUserDatabase())
        }
    }
}