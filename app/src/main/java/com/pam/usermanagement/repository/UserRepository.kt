package com.pam.usermanagement.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pam.usermanagement.database.UserDatabase
import com.pam.usermanagement.database.asUserModel
import com.pam.usermanagement.helper.TOKEN
import com.pam.usermanagement.models.User
import com.pam.usermanagement.network.RetrofitClient
import com.pam.usermanagement.network.asUserDatabase
import com.pam.usermanagement.network.asUserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val database: UserDatabase, private val login: String? = null) {
    val users: LiveData<List<User>> = Transformations.map(
        database.userDao.getUsers()
    ) {
        it.asUserModel()
    }


    val user: LiveData<User>? = login?.let {
        Transformations.map(
            database.userDao.getUser(login)
        ) {
            it.asUserModel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {
            val users = RetrofitClient.getInstance().getUsers(TOKEN)
            database.userDao.insertAll(users.asUserDatabase())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUser() {
        if (login != null) {
            Log.d("getUser", login)

            withContext(Dispatchers.IO) {
                val user = RetrofitClient.getInstance().getUserInfo(TOKEN, login).asUserInfo()
                database.userDao.updateUserInfo(user.name,user.bio,user.blog,user.created_at,user.followers,user.email,user.location, login)
            }
        }
    }

}