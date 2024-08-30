package com.buytown.ru.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.buytown.ru.R
import com.buytown.ru.data.repository.UserRepository
import com.buytown.ru.data.network.User
import com.buytown.ru.utils.Resource
import com.buytown.ru.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context,  // Использование @ApplicationContext для контекста
) : ViewModel() {

    private val _loginResult = MutableLiveData<Resource<String>>()
    val loginResult: LiveData<Resource<String>> = _loginResult

    private val _registerResult = MutableLiveData<Resource<Unit>>()
    val registerResult: LiveData<Resource<Unit>> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private lateinit var navController: NavController

    fun register(username: String, email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                userRepository.register(User(username, email, password))
                _registerResult.value = Resource.success(Unit)
            } catch (e: Exception) {
                _registerResult.value = Resource.error("Не удалось зарегистрироваться. Попробуйте еще раз.")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(email: String, password: String, navController: NavController) {
        _isLoading.value = true
        this.navController = navController
        viewModelScope.launch {
            try {
                val token = userRepository.login(email, password)
                TokenManager.saveToken(context, token)  // Сохранение токена после успешного входа
                _loginResult.value = Resource.success(token)
                navigateToMain()
            } catch (e: Exception) {
                _loginResult.value = Resource.error("Неверный логин или пароль. Попробуйте еще раз.")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun navigateToMain() {
        navController.navigate(R.id.action_loginFragment_to_productFragment)
    }

}
