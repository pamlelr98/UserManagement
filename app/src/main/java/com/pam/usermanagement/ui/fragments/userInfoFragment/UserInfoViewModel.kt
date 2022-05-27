package com.pam.usermanagement.ui.fragments.userInfoFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.pam.usermanagement.helper.TOKEN
import com.pam.usermanagement.network.NetworkUserInfo
import com.pam.usermanagement.network.RetrofitClient
import kotlinx.coroutines.launch
import java.io.IOException

class UserInfoViewModel(
    private val login: String,
    private val avatar_url: String,
    val application: Application
) : ViewModel() {

    private val _user = MutableLiveData<NetworkUserInfo>()
    val user: LiveData<NetworkUserInfo> get() = _user

    var avatar: String = ""

    init {
        getUserInfoFromNetwork()
    }

    private fun getUserInfoFromNetwork() {

        avatar = avatar_url

        viewModelScope.launch {
            try {
                _user.value = RetrofitClient.getInstance().getUserInfo(TOKEN, login)
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