package com.pam.usermanagement.viewmodels

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.pam.usermanagement.database.getDatabase
import com.pam.usermanagement.helper.TOKEN
import com.pam.usermanagement.network.NetworkUserInfo
import com.pam.usermanagement.network.RetrofitClient
import com.pam.usermanagement.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.O)
class UserInfoViewModel(
    private val login: String,
    private val avatar_url: String,
    private val application: Application
) : ViewModel() {

    private val userRepository = UserRepository(getDatabase(application), login)

    val user = userRepository.user

    var avatar: String = ""

    init {
        getUserInfoFromNetwork()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getUserInfoFromNetwork() {

        avatar = avatar_url

        viewModelScope.launch {
            try {
                userRepository.getUser()
            } catch (networkError: IOException) {
                Log.d("UserInfoViewModel", networkError.toString())
            }
        }
    }

    class Factory(
        private val login: String,
        private val avatar_url: String,
        private val application: Application
    ) :
        ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
                return UserInfoViewModel(login, avatar_url, application) as T
            }
            throw IllegalArgumentException("unable known modelView")
        }

    }
}