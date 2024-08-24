package com.buytown.ru.ui.login

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

    fun register(username: String, email: String, password: String, onResult: (Resource<Unit>) -> Unit) {
        viewModelScope.launch {
            try {
                userRepository.register(User(username, email, password))
                onResult(Resource.success(Unit))
            } catch (e: Exception) {
                onResult(Resource.error(e.localizedMessage ?: "Error"))
            }
        }
    }

    fun login(email: String, password: String, onResult: (Resource<String>) -> Unit) {
        viewModelScope.launch {
            try {
                val token = userRepository.login(email, password)
                onResult(Resource.success(token))
            } catch (e: Exception) {
                onResult(Resource.error(e.localizedMessage ?: "Error"))
            }
        }
    }
}
