package com.pam.usermanagement.ui.fragments.usersFragment

import android.app.Application
import androidx.lifecycle.*
import com.pam.usermanagement.database.getDatabase
import com.pam.usermanagement.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.IOException

enum class UsersApiStatus { LOADING, ERROR, DONE }

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(getDatabase(application))

    val users = userRepository.users

    private val _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetWorkError: LiveData<Boolean> get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetWorkErrorShown: LiveData<Boolean> get() = _isNetworkErrorShown

    private val _status = MutableLiveData<UsersApiStatus>()
    val status: LiveData<UsersApiStatus> get() = _status

    init {
        refreshDataFromRepository()
    }

    fun refreshDataFromRepository() {
        _status.value = UsersApiStatus.LOADING
        viewModelScope.launch {
            try {
                userRepository.refreshUsers()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
                _status.value = UsersApiStatus.DONE
            } catch (networkError: IOException) {
                if (users.value.isNullOrEmpty()) {
                    _eventNetworkError.value = true
                    _status.value = UsersApiStatus.ERROR
                }
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
                return UsersViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct to viewModel")
        }
    }
}