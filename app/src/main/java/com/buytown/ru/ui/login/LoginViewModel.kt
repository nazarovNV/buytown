package com.buytown.ru.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buytown.ru.data.repository.UserRepository
import com.buytown.ru.data.network.User
import com.buytown.ru.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<Resource<String>>()
    val loginResult: LiveData<Resource<String>> = _loginResult

    private val _registerResult = MutableLiveData<Resource<Unit>>()
    val registerResult: LiveData<Resource<Unit>> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(username: String, email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                userRepository.register(User(username, email, password))
                _registerResult.value = Resource.success(Unit)
            } catch (e: Exception) {
                _registerResult.value = Resource.error(e.localizedMessage ?: "Error")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val token = userRepository.login(email, password)
                _loginResult.value = Resource.success(token)
            } catch (e: Exception) {
                _loginResult.value = Resource.error(e.localizedMessage ?: "Error")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
