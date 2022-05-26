package com.pam.usermanagement.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.pam.usermanagement.database.getDatabase
import com.pam.usermanagement.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

enum class UsersApiStatus { LOADING, ERROR, DONE }

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(getDatabase(application))

    val users = userRepository.users

    private val _eventNetworkError = MutableLiveData(false)

    val eventNetWorkError: LiveData<Boolean> get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData(false)
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
                delay(1000)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
                _status.value = UsersApiStatus.DONE
            } catch (networkError: IOException) {
                delay(1000)
                _isNetworkErrorShown.value = true
                _eventNetworkError.value = true
                _status.value = UsersApiStatus.ERROR
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = false
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
                return UsersViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct to viewModel")
        }
    }
}